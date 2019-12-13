package databuilder.configs;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import databuilder.AppConfig;
import databuilder.data.db_managers.consumer.ConsumerManager;
import databuilder.data.db_managers.consumer.DBConsumerManager;
import databuilder.data.db_managers.product.DBProductManager;
import databuilder.data.db_managers.product.ProductManager;
import databuilder.data.persistence.StoredConsumerDetails;
import databuilder.data.persistence.StoredProductDetails;
import io.dropwizard.sharding.DBShardingBundle;
import io.dropwizard.sharding.dao.RelationalDao;

public class DbfConfigModule extends AbstractModule {

    private DBShardingBundle<AppConfig> shardingBundle;

    public DbfConfigModule(DBShardingBundle<AppConfig> shardingBundle) {
        this.shardingBundle = shardingBundle;
    }

    protected void configure() {

        bind(ConsumerManager.class)
                .to(DBConsumerManager.class);
        bind(ProductManager.class)
                .to(DBProductManager.class);
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
}
