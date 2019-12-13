package databuilder.data.db_managers.product;

import databuilder.data.models.ProductDetailsData;
import databuilder.data.models.requests.CreateProductRequest;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ProductManager {

    <T> Optional<T>  createProduct(CreateProductRequest createProductRequest, Function<ProductDetailsData, T> handler);

    <T> Optional <T> getProduct(String productName, Function<ProductDetailsData, T> handler);

    <T> List<T> getProducts(Function<ProductDetailsData, T> handler);
}
