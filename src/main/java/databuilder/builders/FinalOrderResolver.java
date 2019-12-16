package databuilder.builders;

import com.flipkart.databuilderframework.annotations.DataBuilderClassInfo;
import com.flipkart.databuilderframework.engine.DataBuilder;
import com.flipkart.databuilderframework.engine.DataBuilderContext;
import com.flipkart.databuilderframework.model.Data;
import databuilder.data.models.ConsumerProductPriceOrder;
import databuilder.data.models.DeliveryAddressData;
import databuilder.data.models.OrderSummaryData;
import databuilder.data.models.PhaseTwoStarter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@DataBuilderClassInfo(
        name = FinalOrderResolver.NAME,
        consumes = {
                ConsumerProductPriceOrder.class,
                DeliveryAddressData.class,
                PhaseTwoStarter.class
        },
        produces = OrderSummaryData.class
)
@Slf4j
public class FinalOrderResolver extends DataBuilder {

    public static final String NAME = "FINAL_ORDER_RESOLVER";

    @Override
    public Data process(DataBuilderContext dataBuilderContext) {
        val accessor = dataBuilderContext.getDataSet(this).accessor();
        val consumerProductPriceOrder = accessor.get(ConsumerProductPriceOrder.class);
        val deliveryAddress = accessor.get(DeliveryAddressData.class).getDeliveryAddress();

        log.info("Inside Final Order Resolver Builder : ");
        log.info("Delivery address - " + deliveryAddress);

        return OrderSummaryData.builder()
                .consumerName(consumerProductPriceOrder.getConsumerDetailsData().getConsumerName())
                .consumerMobile(consumerProductPriceOrder.getConsumerDetailsData().getMobileNumber())
                .productName(consumerProductPriceOrder.getProductDetailsData().getProductName())
                .productDescription(consumerProductPriceOrder.getProductDetailsData().getProductDescription())
                .orderAmount(consumerProductPriceOrder.getOrderAmount())
                .deliveryAddress(deliveryAddress)
                .build();
    }
}
