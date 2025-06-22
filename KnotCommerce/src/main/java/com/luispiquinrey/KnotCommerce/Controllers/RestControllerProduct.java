package com.luispiquinrey.KnotCommerce.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luispiquinrey.KnotCommerce.Configuration.RabbitAMQP.RabbitMQPublisher;
import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;
import com.luispiquinrey.KnotCommerce.Service.IServiceProduct;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Tag(
    name = "CRUD Repsitory for Products",
    description = "CRUD REST API to CREATE,UPDATE,FETCH AND DELETE"
)
@RestController
public class RestControllerProduct {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerProduct.class);

    @Autowired
    private final IServiceProduct iServiceProduct;

    @Autowired
    private final RabbitMQPublisher rabbitMQPublisher;

    public RestControllerProduct(IServiceProduct iServiceProduct,RabbitMQPublisher rabbitMQPublisher){
        this.iServiceProduct = iServiceProduct;
        this.rabbitMQPublisher=rabbitMQPublisher;
    }

    @Operation(
        summary = "Create products",
        description = "Endpoint to create new products",
        method="POST",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product object to be created",
            required = true
        )
    )
    @ApiResponse(
        responseCode = "201",
        description = "HTTP Status CREATED"
    )
    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product, BindingResult binding) throws Exception {
        if(binding.hasErrors()){
            StringBuilder sb = new StringBuilder();
            binding.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(sb.toString().trim());
        }
        try {
            iServiceProduct.createProduct(product);

            rabbitMQPublisher.sendMessageStripe(product.productToJson());


            return ResponseEntity.status(HttpStatus.CREATED)
                .body(product.toString() );
        } catch (ProductCreationException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @Operation(
        summary = "Delete product",
        description = "Endpoint to delete a product by ID",
        method="DELETE",
        parameters = {
            @Parameter(
                name = "id",
                description = "ID of the product to delete",
                required = true
            )
        }
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try{
            iServiceProduct.deleteProductById(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body("Product with the id: " + id + " correctly deleted");
        }catch(ProductDeleteException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @Operation(
        summary = "Update product",
        description = "Endpoint to update an existing product",
        method="CREATE",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product object to be created",
            required = true
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        try{
            iServiceProduct.updateProduct(product);
            return ResponseEntity.status(HttpStatus.OK)
                .body("Product with id: " + product.getId_Product() + " correctly updated");
        }catch(ProductUpdateException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @Operation(
        summary = "Get product by ID",
        description = "Endpoint to retrieve product details by ID",
        method="GET",
        parameters = {
            @Parameter(
                name = "id",
                description = "ID of the product to delete",
                required = true
            )
        }
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            Product product = iServiceProduct.getProductOrThrow(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(product.productToJson());
        }catch(EntityNotFoundException | JsonProcessingException e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @Operation(
        summary = "List available products",
        description = "Endpoint to retrieve all products with stock",
        method="GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @GetMapping("/availableProducts")
    public ResponseEntity<?> getAvailableProducts() {
        try {
            return ResponseEntity.ok(iServiceProduct.findAvailableProducts());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving available products: " + e.getMessage());
        }
    }

    @Operation(
        summary = "List products by category",
        description = "Endpoint to retrieve products based on category name",
        method="GET",
        parameters={
            @Parameter(
                name="categoryName",
                description="Category to find the products",
                required=true
            )
        }
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @GetMapping("/productsByCategory/{categoryName}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryName) {
        try {
            return ResponseEntity.ok(iServiceProduct.findByCategoryName(categoryName));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving products by category: " + e.getMessage());
        }
    }

    @Operation(
        summary = "List products by price range",
        description = "Endpoint to retrieve products within a price range",
        method="GET",
        parameters={
            @Parameter(
                name="minPrice",
                description="The min price of the range"
            ),
            @Parameter(
                name="maxPrice",
                description="The max price of the range"
            )
        }
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @GetMapping("/productsByPriceRange")
    public ResponseEntity<?> getProductsByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        try {
            return ResponseEntity.ok(iServiceProduct.findByPriceRange(minPrice, maxPrice));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving products by price range: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Delete products by category",
        description = "Endpoint to delete all products in a specific category",
        method="DELETE",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Category to delete products",
            required = true
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @DeleteMapping("/deleteByCategory")
    public ResponseEntity<?> deleteByCategory(@RequestBody Category category) {
        try {
            iServiceProduct.deleteByCategory(category);
            return ResponseEntity.ok("Products in category '" + category.getName() + "' deleted successfully!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @Operation(
        summary = "Update product stock",
        description = "Endpoint to update the stock quantity of a product",
        method="PUT",
        parameters={
            @Parameter(
                name="id",
                description="Id to update the Stock"
            ),
            @Parameter(
                name="stock",
                description="Stock of the product to update"
            )
        }
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @PutMapping("/updateStock/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam int stock) {
        try {
            iServiceProduct.updateStock(id, stock);
            return ResponseEntity.ok("Stock updated for product with id: " + id);
        } catch (ProductUpdateException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @Operation(
        summary = "List all products",
        description = "Endpoint to retrieve all products in the system",
        method="GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @GetMapping("/findAllProducts")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(iServiceProduct.findAllProducts());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving all products: " + e.getMessage());
        }
    }
}
