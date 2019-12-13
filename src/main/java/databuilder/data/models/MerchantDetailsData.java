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
public class MerchantDetailsData extends DbfData<MerchantDetailsData> {

    @NotNull
    @NotEmpty
    private String merchantName;

    @NotNull
    @NotEmpty
    private String mobileNumber;

    @Builder
    public MerchantDetailsData(String merchantName, String mobileNumber) {
        this();
        this.merchantName = merchantName;
        this.mobileNumber = mobileNumber;
    }

    protected MerchantDetailsData() {
        super(MerchantDetailsData.class);
    }
}
