package databuilder.data.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderSummaryData extends DbfData<OrderSummaryData> {

    private String consumerName;
    private String consumerMobile;
    private String productName;
    private String productDescription;
    private String deliveryAddress;
    private int orderAmount;

    @Builder
    public OrderSummaryData(String consumerName, String consumerMobile, String productName, String productDescription, String deliveryAddress, int orderAmount) {
        this();
        this.consumerName = consumerName;
        this.consumerMobile = consumerMobile;
        this.productName = productName;
        this.productDescription = productDescription;
        this.deliveryAddress = deliveryAddress;
        this.orderAmount = orderAmount;
    }

    protected OrderSummaryData() {
        super(OrderSummaryData.class);
    }
}
