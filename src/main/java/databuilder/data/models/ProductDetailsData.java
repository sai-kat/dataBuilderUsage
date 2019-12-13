package databuilder.data.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDetailsData extends DbfData<ProductDetailsData> {

    private long productId;

    @NotNull
    @NotEmpty
    private String productName;

    @NotNull
    @NotEmpty
    private String productDescription;

    @Min(1)
    @Max(99)
    private int quantity;

    @Builder
    public ProductDetailsData(long productId, String productName, String productDescription, int quantity) {
        this();
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantity = quantity;
    }

    public ProductDetailsData() {
        super(ProductDetailsData.class);
    }
}
