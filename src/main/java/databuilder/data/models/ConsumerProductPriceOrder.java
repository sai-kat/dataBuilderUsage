package databuilder.data.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConsumerProductPriceOrder extends DbfData<ConsumerProductPriceOrder> {

    @NotNull
    private ConsumerDetailsData consumerDetailsData;

    @NotNull
    private ProductDetailsData productDetailsData;

    @Min(1)
    private int orderAmount;

    @Builder
    public ConsumerProductPriceOrder(ConsumerDetailsData consumerDetailsData, ProductDetailsData productDetailsData, int orderAmount) {
        this();
        this.consumerDetailsData = consumerDetailsData;
        this.productDetailsData = productDetailsData;
        this.orderAmount = orderAmount;
    }

    protected ConsumerProductPriceOrder() {
        super(ConsumerProductPriceOrder.class);
    }
}
