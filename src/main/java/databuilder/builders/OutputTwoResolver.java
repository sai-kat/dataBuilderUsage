package databuilder.builders;

import com.flipkart.databuilderframework.annotations.DataBuilderClassInfo;
import com.flipkart.databuilderframework.engine.DataBuilder;
import com.flipkart.databuilderframework.engine.DataBuilderContext;
import com.flipkart.databuilderframework.model.Data;
import databuilder.data.InputLevelOneData;
import databuilder.data.OutputLevelTwoData;
import databuilder.data.TransactionSummary;
import lombok.val;

@DataBuilderClassInfo(
        name = OutputTwoResolver.NAME,
        consumes = {
                TransactionSummary.class,
                InputLevelOneData.class
        },
        produces = OutputLevelTwoData.class
)
public class OutputTwoResolver extends DataBuilder {

    public static final String NAME = "OUTPUT_TWO_RESOLVER";

    public Data process(DataBuilderContext dataBuilderContext) {

        val accessor = dataBuilderContext.getDataSet(this).accessor();
        val transactionSummaryInput = accessor.get(TransactionSummary.class);
        val levelOneInput = accessor.get(InputLevelOneData.class);

        System.out.println("Inside builder" + NAME + " : ");
        System.out.println("\t transaction summary details - " + transactionSummaryInput.getTransactionId() + " " + transactionSummaryInput.getConsumerName() + " " + transactionSummaryInput.getMerchantName());

        return OutputLevelTwoData.builder()
                .someOutputValue(transactionSummaryInput.getTransactionId() + levelOneInput.getSomeInputValue())
                .build();
    }
}
