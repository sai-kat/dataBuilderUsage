package databuilder.data.db_managers.flow;

import com.flipkart.databuilderframework.model.DataFlowInstance;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import databuilder.DataBuilderError;
import databuilder.MapperUtils;
import databuilder.data.flows.FlowState;
import databuilder.data.flows.OrderFlow;
import databuilder.data.flows.StoredOrderFlow;
import io.dropwizard.sharding.dao.LookupDao;
import org.apache.commons.jcs.utils.zip.CompressionUtil;

import java.util.Optional;

@Singleton
public class DBOrderFlowCommands implements OrderFlowCommands {

    private final LookupDao<StoredOrderFlow> storedOrderFlowLookupDao;

    @Inject
    public DBOrderFlowCommands(LookupDao<StoredOrderFlow> storedOrderFlowLookupDao) {
        this.storedOrderFlowLookupDao = storedOrderFlowLookupDao;
    }

    @Override
    public Optional<OrderFlow> saveOrderFlow(DataFlowInstance dataFlowInstance) {

        try {
            StoredOrderFlow storedOrderFlow = StoredOrderFlow.builder()
                    .flowId(dataFlowInstance.getId())
                    .data(MapperUtils.serialize(dataFlowInstance))
                    .state(FlowState.CREATED)
                    .build();
            compress(storedOrderFlow);
            return storedOrderFlowLookupDao.save(storedOrderFlow)
                    .map(this :: toWired);
        }catch (Exception e) {
            throw new DataBuilderError("Could not save order flow in DB");
        }
    }

    public void compress(StoredOrderFlow storedOrderFlow) throws Exception {
        byte[] data = CompressionUtil.compressByteArray(storedOrderFlow.getData());
        storedOrderFlow.setData(data);
    }

    public byte[] decompress(byte[] data){
        return CompressionUtil.decompressByteArray(data);
    }

    public OrderFlow toWired(StoredOrderFlow storedOrderFlow){
        return OrderFlow.builder()
                .flowId(storedOrderFlow.getFlowId())
                .dataFlowInstance(MapperUtils.deserialize(decompress(storedOrderFlow.getData()), DataFlowInstance.class))
                .created(storedOrderFlow.getCreated())
                .updated(storedOrderFlow.getUpdated())
                .build();
    }

    @Override
    public Optional<OrderFlow> getOrderFlow(String flowId) {
        try {
            return storedOrderFlowLookupDao.get(flowId)
                    .map(this :: toWired);
        } catch (Exception e) {
            throw new DataBuilderError("Could not get data flow from DB");
        }
    }

    @Override
    public boolean updateOrderFlow(DataFlowInstance dataFlowInstance) {
            storedOrderFlowLookupDao.update(dataFlowInstance.getId(), storedOrderFlowOptional -> {
                if (!storedOrderFlowOptional.isPresent())
                    return null;
                StoredOrderFlow storedOrderFlow = storedOrderFlowOptional.get();
                storedOrderFlow.setData(MapperUtils.serialize(dataFlowInstance));
                try {
                    compress(storedOrderFlow);
                } catch (Exception e) {
                    throw new DataBuilderError("Could not compress data");
                }
                return storedOrderFlow;
            });
            OrderFlow orderFlow = getOrderFlow(dataFlowInstance.getId()).orElse(null);
        return true;
    }
}
