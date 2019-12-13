package databuilder.executors;

import com.flipkart.databuilderframework.engine.*;
import com.flipkart.databuilderframework.model.DataExecutionResponse;
import com.flipkart.databuilderframework.model.DataFlow;
import databuilder.builders.OutputTwoResolver;
import databuilder.builders.TransactionSummaryResolver;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.InputLevelOneData;
import databuilder.data.models.MerchantDetailsData;
import databuilder.data.models.OutputLevelTwoData;

import java.io.IOException;

public class FlowExecutor {

    public void run() throws DataBuilderFrameworkException, DataValidationException {

        DataBuilderMetadataManager dataBuilderMetadataManager = new DataBuilderMetadataManager();
        dataBuilderMetadataManager
                .register(OutputTwoResolver.class)
                .register(TransactionSummaryResolver.class);

        final DataFlow createdFlowTest = new DataFlowBuilder()
                .withMetaDataManager(dataBuilderMetadataManager)
                .withTargetData(OutputLevelTwoData.class)
                .build();

        final DataFlowExecutor executor = new SimpleDataFlowExecutor();

        ConsumerDetailsData testDataA = ConsumerDetailsData.builder()
                .consumerName("xyz")
                .mobileNumber("1234")
                .build();

        MerchantDetailsData testDataB = MerchantDetailsData.builder()
                .merchantName("qwer")
                .mobileNumber("7890")
                .build();

        InputLevelOneData testDataC = InputLevelOneData.builder()
                .someInputValue("some input value here")
                .build();
        DataExecutionResponse response =  executor.run(createdFlowTest, testDataA, testDataB, testDataC);
        OutputLevelTwoData outputLevelTwoData = response.get(OutputLevelTwoData.class);
        System.out.println(outputLevelTwoData.getSomeOutputValue());
    }

    public static void main(String args[]) throws IOException {
        try {
            new FlowExecutor().run();
        }catch (Exception ignored){}
    }
}
