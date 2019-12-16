package databuilder.data.db_managers.flow;

import com.flipkart.databuilderframework.model.DataFlowInstance;
import databuilder.data.flows.OrderFlow;

import java.util.Optional;

public interface OrderFlowCommands {

    Optional<OrderFlow> saveOrderFlow(DataFlowInstance dataFlowInstance);

    Optional<OrderFlow> getOrderFlow(String flowId);

    boolean updateOrderFlow(DataFlowInstance dataFlowInstance);
}
