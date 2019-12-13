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
public class TransactionSummary extends DbfData<TransactionSummary> {

    @NotNull
    @NotEmpty
    private String transactionId;

    @NotNull
    @NotEmpty
    private String consumerName;

    @NotNull
    @NotEmpty
    private String merchantName;

    @Builder
    public TransactionSummary(String transactionId, String consumerName, String merchantName) {
        this();
        this.transactionId = transactionId;
        this.consumerName = consumerName;
        this.merchantName = merchantName;
    }

    protected TransactionSummary() {
        super(TransactionSummary.class);
    }
}
