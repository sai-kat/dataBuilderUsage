package databuilder.executors;

import com.flipkart.databuilderframework.engine.DataBuilderMetadataManager;
import com.flipkart.databuilderframework.engine.DataFlowBuilder;
import com.flipkart.databuilderframework.engine.DataFlowExecutor;
import com.flipkart.databuilderframework.engine.SimpleDataFlowExecutor;
import com.flipkart.databuilderframework.model.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import databuilder.DataBuilderError;
import databuilder.SimpleBuilderFactory;
import databuilder.builders.ConsumerResolver;
import databuilder.builders.FinalOrderResolver;
import databuilder.builders.OrderAmountResolver;
import databuilder.builders.ProductResolver;
import databuilder.data.db_managers.flow.OrderFlowCommands;
import databuilder.data.flows.OrderFlow;
import databuilder.data.models.OrderSummaryData;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Singleton
@Slf4j
public class OrderFlowExecutor {

    private DataFlow orderFlow;
    private DataFlowExecutor orderFlowExecutor;
    private final OrderFlowCommands orderFlowCommands;
    private final Injector injector;

    public static final String flowId = "ORDER_FLOW";

    @Inject
    public OrderFlowExecutor(OrderFlowCommands orderFlowCommands, Injector injector) {
        this.orderFlowCommands = orderFlowCommands;
        this.injector = injector;
        initialize();
    }

    public void initialize() {
        this.orderFlow = createFlow();
        Preconditions.checkNotNull(orderFlow);
        this.orderFlowExecutor = new SimpleDataFlowExecutor(new SimpleBuilderFactory(injector));
        start(OrderFlowExecutor.flowId);
    }

    protected DataFlow createFlow() {
        try {
            DataBuilderMetadataManager dataBuilderMetadataManager = new DataBuilderMetadataManager();
            dataBuilderMetadataManager
                    .register(ConsumerResolver.class)
                    .register(ProductResolver.class)
                    .register(OrderAmountResolver.class)
                    .register(FinalOrderResolver.class)
            ;
            return new DataFlowBuilder()
                    .withMetaDataManager(dataBuilderMetadataManager)
                    .withTargetData(OrderSummaryData.class)
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    protected DataFlowInstance createStartInstance(String flowId, DataSet dataSet) {
        return new DataFlowInstance(flowId, orderFlow.deepCopy(), dataSet);
    }

    public void start(String flowId, Data... inputData) {
        DataFlowInstance dataFlowInstance = createStartInstance(flowId, new DataSet());
        orderFlowCommands.saveOrderFlow(dataFlowInstance);
        List<Data> data = inputData == null ? Lists.newArrayList() : Arrays.asList(inputData);
        execute(dataFlowInstance.getId(), new DataDelta(data));
    }

    public void execute(String flowId, DataDelta dataDelta) {
        OrderFlow orderFlow = orderFlowCommands.getOrderFlow(flowId)
                .orElse(null);
        Preconditions.checkNotNull(orderFlow);
        DataFlowInstance dataFlowInstance = orderFlow.getDataFlowInstance();
        try {
            DataExecutionResponse dataExecutionResponse = orderFlowExecutor.run(dataFlowInstance, dataDelta);
            orderFlowCommands.updateOrderFlow(orderFlow.getDataFlowInstance());
        } catch (Exception e) {
            log.error("Error: ", e);
            throw new DataBuilderError("Could not execute the data flow instance with the datadelta");
        }
    }
}
