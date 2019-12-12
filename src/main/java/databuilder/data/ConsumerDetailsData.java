package databuilder.data;

import databuilder.DbfData;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConsumerDetailsData extends DbfData<ConsumerDetailsData> {

    @NotNull
    @NotEmpty
    private String consumerName;

    @NotNull
    @NotEmpty
    private String mobileNumber;

    @Builder
    public ConsumerDetailsData(String consumerName, String mobileNumber) {
        this();
        this.consumerName = consumerName;
        this.mobileNumber = mobileNumber;
    }

    protected ConsumerDetailsData() {
        super(ConsumerDetailsData.class);
    }
}
