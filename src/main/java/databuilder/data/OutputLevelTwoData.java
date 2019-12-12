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
public class OutputLevelTwoData extends DbfData<OutputLevelTwoData> {

    @NotNull
    @NotEmpty
    private String someOutputValue;

    @Builder
    public OutputLevelTwoData(String someOutputValue) {
        this();
        this.someOutputValue = someOutputValue;
    }

    protected OutputLevelTwoData() {
        super(OutputLevelTwoData.class);
    }
}
