package databuilder.data.models;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderRequestInput extends DbfData<OrderRequestInput> {

    @NotNull
    @NotEmpty
    private String customerMobile;

    @NotNull
    @NotEmpty
    private String productName;

    @Min(1)
    private int quantity;

    @Builder
    public OrderRequestInput(String customerMobile, String productName, int quantity) {
        this();
        this.customerMobile = customerMobile;
        this.productName = productName;
        this.quantity = quantity;
    }

    protected OrderRequestInput() {
        super(OrderRequestInput.class);
    }
}
