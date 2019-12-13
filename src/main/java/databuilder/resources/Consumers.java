package databuilder.resources;

import com.google.inject.Inject;
import databuilder.data.db_managers.consumer.ConsumerManager;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.requests.CreateConsumerRequest;
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

    @Inject
    public Consumers(ConsumerManager consumerManager) {
        this.consumerManager = consumerManager;
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
