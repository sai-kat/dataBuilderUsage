package databuilder.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConsumerDetailsData extends DbfData<ConsumerDetailsData> {

    @JsonIgnore
    private long consumerId;

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
