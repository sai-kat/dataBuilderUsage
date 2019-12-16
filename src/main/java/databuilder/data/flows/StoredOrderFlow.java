package databuilder.data.flows;

import io.dropwizard.sharding.sharding.LookupKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_data_flows")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoredOrderFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @LookupKey
    @Column(name = "flow_id", nullable = false, unique = true)
    private String flowId;

    @Column(name = "state", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FlowState state;

    @Column(name = "data", columnDefinition = "blob")
    private byte[] data;

    @Column(name = "created", columnDefinition = "datetime(3) default current_timestamp(3)", updatable = false, insertable = false)
    @Generated(value = GenerationTime.INSERT)
    private Date created;

    @Column(name = "updated", columnDefinition = "datetime(3) default current_timestamp(3)", updatable = false, insertable = false)
    @Generated(value = GenerationTime.ALWAYS)
    private Date updated;
}
