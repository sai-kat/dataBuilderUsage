package databuilder.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhaseTwoStarter extends DbfData<PhaseTwoStarter> {
    public PhaseTwoStarter() {
        super(PhaseTwoStarter.class);
    }
}
