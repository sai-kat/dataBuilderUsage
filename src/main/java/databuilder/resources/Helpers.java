package databuilder.resources;

import com.flipkart.databuilderframework.model.DataDelta;
import com.google.inject.Inject;
import databuilder.data.models.DeliveryAddressData;
import databuilder.data.models.OrderRequestInput;
import databuilder.data.models.PhaseOneStarter;
import databuilder.data.models.PhaseTwoStarter;
import databuilder.data.models.requests.CreateDeliveryAddressRequest;
import databuilder.executors.OrderFlowExecutor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
@Path("/helpers")
@Slf4j
public class Helpers {

    private final OrderFlowExecutor orderFlowExecutor;

    @Inject
    public Helpers(OrderFlowExecutor orderFlowExecutor) {
        this.orderFlowExecutor = orderFlowExecutor;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/create/orderRequest")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderRequestInput addOrderRequest(@Valid OrderRequestInput orderRequestInput){
        if(orderRequestInput != null)
            orderFlowExecutor.execute(OrderFlowExecutor.flowId, new DataDelta(orderRequestInput));
        return orderRequestInput;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/create/phaseOneStarter")
    @Produces(MediaType.APPLICATION_JSON)
    public PhaseOneStarter addPhaseOneStarter(){
        PhaseOneStarter phaseOneStarter = PhaseOneStarter.builder().build();
        OrderRequestInput orderRequestInput = OrderRequestInput.builder()
                .customerMobile("8584965037")
                .productName("earphones")
                .quantity(10)
                .build();
        orderFlowExecutor.execute(OrderFlowExecutor.flowId, new DataDelta(orderRequestInput, phaseOneStarter));
        return phaseOneStarter;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/create/phaseTwoStarter")
    @Produces(MediaType.APPLICATION_JSON)
    public PhaseTwoStarter addPhaseTwoStarter(){
        orderFlowExecutor.execute(OrderFlowExecutor.flowId, new DataDelta(new PhaseTwoStarter()));
        return new PhaseTwoStarter();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/create/delivery")
    @Produces(MediaType.APPLICATION_JSON)
    public DeliveryAddressData addDeliveryAddress(@Valid CreateDeliveryAddressRequest createDeliveryAddressRequest){
        DeliveryAddressData deliveryAddressData = DeliveryAddressData.builder()
                .deliveryAddress(createDeliveryAddressRequest.getDeliveryAddress())
                .customerMobile(createDeliveryAddressRequest.getCustomerMobile())
                .build();
        if(deliveryAddressData != null)
            orderFlowExecutor.execute(OrderFlowExecutor.flowId, new DataDelta(deliveryAddressData));
        return deliveryAddressData;
    }
}
