package databuilder.data.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeliveryAddressRequest {
    @NotNull
    @NotEmpty
    private String customerMobile;

    @NotNull
    @NotEmpty
    private String deliveryAddress;
}
