package databuilder.data.db_managers.product;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import databuilder.DataBuilderError;
import databuilder.data.models.ProductDetailsData;
import databuilder.data.models.requests.CreateProductRequest;
import databuilder.data.persistence.StoredProductDetails;
import io.dropwizard.sharding.dao.RelationalDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
public class DBProductManager implements ProductManager {

    private final RelationalDao<StoredProductDetails> productDetailsRelationalDao;

    @Inject
    public DBProductManager(RelationalDao<StoredProductDetails> productDetailsRelationalDao) {
        this.productDetailsRelationalDao = productDetailsRelationalDao;
    }

    @Override
    public <T> Optional<T> createProduct(CreateProductRequest createProductRequest, Function<ProductDetailsData, T> handler) {
        try{
            StoredProductDetails storedProductDetails = StoredProductDetails.builder()
                    .productName(createProductRequest.getProductName())
                    .productDescription(createProductRequest.getProductDescription())
                    .price(createProductRequest.getPrice())
                    .build();
            Optional<ProductDetailsData> result = productDetailsRelationalDao.save(createProductRequest.getProductName(), storedProductDetails)
                    .map(this :: toWired);
            return result.map(handler);
        }
        catch (Exception e){
            throw new DataBuilderError("Could not create product");
        }
    }

    @Override
    public <T> Optional<T> getProduct(String productName, Function<ProductDetailsData, T> handler) {
        try{
            return productDetailsRelationalDao.select(
                    productName,
                    DetachedCriteria.forClass(StoredProductDetails.class)
                            .add(Restrictions.eq("productName", productName)),
                    0,
                    1)
                    .stream()
                    .findFirst()
                    .map(this :: toWired)
                    .map(handler);
        }
        catch (Exception e){
            throw new DataBuilderError("Could not get product");
        }
    }

    @Override
    public <T> List<T> getProducts(Function<ProductDetailsData, T> handler) {
        return productDetailsRelationalDao.scatterGather(
                DetachedCriteria.forClass(StoredProductDetails.class))
                .stream()
                .map(this :: toWired)
                .map(handler)
                .collect(Collectors.toList()
                );
    }

    public ProductDetailsData toWired(StoredProductDetails storedProductDetails){
        return ProductDetailsData.builder()
                .productId(storedProductDetails.getId())
                .productDescription(storedProductDetails.getProductDescription())
                .productName(storedProductDetails.getProductName())
                .price(storedProductDetails.getPrice())
                .build();
    }
}
