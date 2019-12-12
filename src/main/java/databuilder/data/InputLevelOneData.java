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
public class InputLevelOneData extends DbfData<InputLevelOneData> {

    @NotNull
    @NotEmpty
    private String someInputValue;

    @Builder
    public InputLevelOneData(String someInputValue) {
        this();
        this.someInputValue = someInputValue;
    }

    protected InputLevelOneData() {
        super(InputLevelOneData.class);
    }
}
