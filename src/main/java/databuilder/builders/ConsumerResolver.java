package databuilder.builders;

import com.flipkart.databuilderframework.annotations.DataBuilderClassInfo;
import com.flipkart.databuilderframework.engine.DataBuilder;
import com.flipkart.databuilderframework.engine.DataBuilderContext;
import com.flipkart.databuilderframework.model.Data;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import databuilder.data.db_managers.consumer.ConsumerManager;
import databuilder.data.db_managers.consumer.DBConsumerManager;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.OrderRequestInput;
import databuilder.data.models.PhaseOneStarter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.function.Function;

@DataBuilderClassInfo(
        name = ConsumerResolver.NAME,
        consumes = {
                OrderRequestInput.class,
                PhaseOneStarter.class
        },
        produces = ConsumerDetailsData.class
)
@Slf4j
public class ConsumerResolver extends DataBuilder {

    public static final String NAME = "CONSUMER_RESOLVER";
    private final ConsumerManager consumerManager;

    @Inject
    public ConsumerResolver(ConsumerManager consumerManager) {
        this.consumerManager = consumerManager;
    }

    @Override
    public Data process(DataBuilderContext dataBuilderContext) {
        val accessor = dataBuilderContext.getDataSet(this).accessor();
        val customerMobile = accessor.get(OrderRequestInput.class).getCustomerMobile();

        ConsumerDetailsData consumerDetailsData = consumerManager.getConsumer(customerMobile, Function.identity())
                .orElse(null);

        Preconditions.checkNotNull(consumerDetailsData);

        log.info("In ConsumerResolver builder : ");
        log.info("Consumer name - " + consumerDetailsData.getConsumerName());
        log.info("Consumer mobile - " + consumerDetailsData.getMobileNumber());

        return consumerDetailsData;
    }
}
