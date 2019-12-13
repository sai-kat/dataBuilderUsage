package databuilder.builders;

import com.flipkart.databuilderframework.annotations.DataBuilderClassInfo;
import com.flipkart.databuilderframework.engine.DataBuilder;
import com.flipkart.databuilderframework.engine.DataBuilderContext;
import com.flipkart.databuilderframework.model.Data;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.MerchantDetailsData;
import databuilder.data.models.TransactionSummary;
import lombok.val;

import java.util.UUID;

@DataBuilderClassInfo(
        name = TransactionSummaryResolver.NAME,
        consumes = {
                ConsumerDetailsData.class,
                MerchantDetailsData.class
        },
        produces = TransactionSummary.class
)
public class TransactionSummaryResolver extends DataBuilder {
    public static final String NAME = "TRANSACTION_SUMMARY_RESOLVER";

        public Data process(DataBuilderContext dataBuilderContext){
                val accessor = dataBuilderContext.getDataSet(this).accessor();
                val consumerDetailsInput = accessor.get(ConsumerDetailsData.class);
                val merchantDetailsInput = accessor.get(MerchantDetailsData.class);

                System.out.println("Inside builder" + NAME + " : ");
                System.out.println("\t Names - " + consumerDetailsInput.getConsumerName() + " " + merchantDetailsInput.getMerchantName());
                System.out.println("\t Mobiles - " + consumerDetailsInput.getMobileNumber() + " " + merchantDetailsInput.getMobileNumber());

                return TransactionSummary.builder()
                        .consumerName(consumerDetailsInput.getConsumerName())
                        .merchantName(merchantDetailsInput.getMerchantName())
                        .transactionId(UUID.randomUUID().toString())
                        .build();
        }
}
