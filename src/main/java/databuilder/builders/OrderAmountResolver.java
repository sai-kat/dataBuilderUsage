package databuilder.builders;

import com.flipkart.databuilderframework.annotations.DataBuilderClassInfo;
import com.flipkart.databuilderframework.engine.DataBuilder;
import com.flipkart.databuilderframework.engine.DataBuilderContext;
import com.flipkart.databuilderframework.model.Data;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.ConsumerProductPriceOrder;
import databuilder.data.models.OrderRequestInput;
import databuilder.data.models.ProductDetailsData;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@DataBuilderClassInfo(
        name = OrderAmountResolver.NAME,
        consumes = {
                OrderRequestInput.class,
                ConsumerDetailsData.class,
                ProductDetailsData.class
        },
        produces = ConsumerProductPriceOrder.class
)
@Slf4j
public class OrderAmountResolver extends DataBuilder {

    public static final String NAME = "ORDER_AMOUNT_RESOLVER";

    @Override
    public Data process(DataBuilderContext dataBuilderContext) {

        val accessor = dataBuilderContext.getDataSet(this).accessor();
        val orderRequestInput = accessor.get(OrderRequestInput.class);
        val consumerDetails = accessor.get(ConsumerDetailsData.class);
        val productDetails = accessor.get(ProductDetailsData.class);

        int orderAmount = productDetails.getPrice() * orderRequestInput.getQuantity();

        log.info("In Order Amount Resolver builder : ");
        log.info("Consumer Mobile - " + consumerDetails.getMobileNumber());
        log.info("Product Name - " + productDetails.getProductName());
        log.info("Order Amount - " + orderAmount);

        return ConsumerProductPriceOrder.builder()
                .consumerDetailsData(consumerDetails)
                .productDetailsData(productDetails)
                .orderAmount(orderAmount)
                .build();
    }
}
