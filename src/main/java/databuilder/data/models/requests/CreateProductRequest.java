package databuilder.data.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotNull
    @NotEmpty
    private String productName;

    @NotNull
    @NotEmpty
    private String productDescription;

    @Min(1)
    @Max(99)
    private int quantity;
}
