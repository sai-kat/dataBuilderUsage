package databuilder.data.persistence;

import io.dropwizard.sharding.sharding.LookupKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@Entity
@Table(name = "consumer_details")
@Audited
@AuditTable("consumer_details_audit")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoredConsumerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "consumer_name", nullable = false)
    private String consumerName;

    @LookupKey
    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

}
