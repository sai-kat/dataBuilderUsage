package databuilder.data.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryAddressData extends DbfData<DeliveryAddressData> {

    @NotNull
    @NotEmpty
    private String customerMobile;

    @NotNull
    @NotEmpty
    private String deliveryAddress;

    @Builder
    public DeliveryAddressData(String customerMobile, String deliveryAddress) {
        this();
        this.customerMobile = customerMobile;
        this.deliveryAddress = deliveryAddress;
    }

    protected DeliveryAddressData() {
        super(DeliveryAddressData.class);
    }
}
