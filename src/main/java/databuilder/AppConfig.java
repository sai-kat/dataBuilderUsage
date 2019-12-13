package databuilder;

import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import io.dropwizard.sharding.config.ShardedHibernateFactory;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig extends Configuration implements AssetsBundleConfiguration {

    @Valid
    @NotNull
    private ShardedHibernateFactory db;


    public AssetsConfiguration getAssetsConfiguration() {
        return null;
    }
}
