package databuilder.builders;

import com.flipkart.databuilderframework.annotations.DataBuilderClassInfo;
import com.flipkart.databuilderframework.engine.DataBuilder;
import com.flipkart.databuilderframework.engine.DataBuilderContext;
import com.flipkart.databuilderframework.model.Data;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import databuilder.data.db_managers.product.DBProductManager;
import databuilder.data.db_managers.product.ProductManager;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.OrderRequestInput;
import databuilder.data.models.ProductDetailsData;
import databuilder.data.models.PhaseOneStarter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.function.Function;

@DataBuilderClassInfo(
        name = ProductResolver.NAME,
        consumes = {
                OrderRequestInput.class,
                PhaseOneStarter.class,
        },
        produces = ProductDetailsData.class
)
@Slf4j
@Singleton
public class ProductResolver extends DataBuilder {

    public static final String NAME = "PRODUCT_RESOLVER";
    private final ProductManager productManager;

    @Inject
    public ProductResolver(ProductManager productManager) {
        this.productManager = productManager;
    }

    @Override
    public Data process(DataBuilderContext dataBuilderContext) {
        val accessor = dataBuilderContext.getDataSet(this).accessor();
        val productName = accessor.get(OrderRequestInput.class).getProductName();

        ProductDetailsData productDetailsData = productManager.getProduct(productName, Function.identity())
                .orElse(null);

        Preconditions.checkNotNull(productDetailsData);

        log.info("In Product Resolver builder : ");
        log.info("Product name - " + productDetailsData.getProductName());
        log.info("Product description - " + productDetailsData.getProductDescription());
        log.info("Product price - " + productDetailsData.getPrice());

        return productDetailsData;
    }
}
