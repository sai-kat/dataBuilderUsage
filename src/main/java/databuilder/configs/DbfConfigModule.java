package databuilder.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import databuilder.AppConfig;
import databuilder.data.db_managers.consumer.ConsumerManager;
import databuilder.data.db_managers.consumer.DBConsumerManager;
import databuilder.data.db_managers.flow.DBOrderFlowCommands;
import databuilder.data.db_managers.flow.OrderFlowCommands;
import databuilder.data.db_managers.product.DBProductManager;
import databuilder.data.db_managers.product.ProductManager;
import databuilder.data.flows.StoredOrderFlow;
import databuilder.data.persistence.StoredConsumerDetails;
import databuilder.data.persistence.StoredProductDetails;
import io.dropwizard.setup.Environment;
import io.dropwizard.sharding.DBShardingBundle;
import io.dropwizard.sharding.dao.LookupDao;
import io.dropwizard.sharding.dao.RelationalDao;

public class DbfConfigModule extends AbstractModule {

    private DBShardingBundle<AppConfig> shardingBundle;

    public DbfConfigModule(DBShardingBundle<AppConfig> shardingBundle) {
        this.shardingBundle = shardingBundle;
    }

    @Override
    protected void configure() {

        bind(ConsumerManager.class)
                .to(DBConsumerManager.class);
        bind(ProductManager.class)
                .to(DBProductManager.class);
        bind(OrderFlowCommands.class)
                .to(DBOrderFlowCommands.class);
    }

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper(Environment environment){
        return environment.getObjectMapper();
    }

    @Provides
    @Singleton
    public RelationalDao<StoredConsumerDetails> provideConsumerDao(){
        return DBShardingBundle.createRelatedObjectDao(shardingBundle, StoredConsumerDetails.class);
    }

    @Provides
    @Singleton
    public RelationalDao<StoredProductDetails> provideProductDao(){
        return DBShardingBundle.createRelatedObjectDao(shardingBundle, StoredProductDetails.class);
    }

    @Provides
    @Singleton
    public LookupDao<StoredOrderFlow> provideOrderFlowDao(){
        return DBShardingBundle.createParentObjectDao(shardingBundle, StoredOrderFlow.class);
    }
}
