package databuilder.resources;

import com.google.inject.Inject;
import databuilder.data.db_managers.product.ProductManager;
import databuilder.data.models.ConsumerDetailsData;
import databuilder.data.models.ProductDetailsData;
import databuilder.data.models.requests.CreateConsumerRequest;
import databuilder.data.models.requests.CreateProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.Function;

@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
@Path("/products")
@Slf4j
public class Products {

    private final ProductManager productManager;

    @Inject
    public Products(ProductManager productManager) {
        this.productManager = productManager;
    }

    @GET
    public List<ProductDetailsData> getAllProducts(){
        return productManager.getProducts(Function.identity());
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDetailsData createProduct(@Valid CreateProductRequest createProductRequest){
        return productManager.createProduct(createProductRequest, Function.identity())
                .orElse(null);
    }

    @GET
    @Path("/{productName}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductDetailsData getConsumer(@NotEmpty @NotNull @PathParam("productName") String productName){
        return productManager.getProduct(productName, Function.identity())
                .orElse(null);
    }
}
