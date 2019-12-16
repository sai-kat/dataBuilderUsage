package databuilder.data.flows;

import com.flipkart.databuilderframework.model.DataFlowInstance;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderFlow {
    private String flowId;
    private DataFlowInstance dataFlowInstance;
    private Date created;
    private Date updated;
}
