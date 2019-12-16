package databuilder.resources;

import com.flipkart.databuilderframework.model.DataDelta;
import com.google.inject.Inject;
import databuilder.data.db_managers.consumer.ConsumerManager;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.requests.CreateConsumerRequest;
import databuilder.executors.OrderFlowExecutor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.Function;

@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
@Path("/consumers")
@Slf4j
public class Consumers{

    private final ConsumerManager consumerManager;
    private final OrderFlowExecutor orderFlowExecutor;

    @Inject
    public Consumers(ConsumerManager consumerManager, OrderFlowExecutor orderFlowExecutor) {
        this.consumerManager = consumerManager;
        this.orderFlowExecutor = orderFlowExecutor;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsumerDetailsData> getAllConsumers(){
        return consumerManager.getConsumers(Function.identity());
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public ConsumerDetailsData createConsumer(@Valid CreateConsumerRequest createConsumerRequest){
        //        if(consumerDetailsData != null)
//            orderFlowExecutor.execute(OrderFlowExecutor.flowId, new DataDelta(consumerDetailsData));
        return consumerManager.createConsumer(createConsumerRequest, Function.identity())
                .orElse(null);
    }

    @GET
    @Path("/{mobileNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public ConsumerDetailsData getConsumer(@NotEmpty @NotNull @PathParam("mobileNumber") String mobileNumber){
        return consumerManager.getConsumer(mobileNumber, Function.identity())
                .orElse(null);
    }
}
