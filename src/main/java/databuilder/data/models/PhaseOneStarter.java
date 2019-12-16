package databuilder.data.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PhaseOneStarter extends DbfData<PhaseOneStarter>{

    public PhaseOneStarter() {
        super(PhaseOneStarter.class);
    }
}
