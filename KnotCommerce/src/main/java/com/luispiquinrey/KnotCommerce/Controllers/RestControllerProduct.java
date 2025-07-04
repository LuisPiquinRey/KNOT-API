package com.luispiquinrey.KnotCommerce.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luispiquinrey.KnotCommerce.Configuration.RabbitAMQP.RabbitMQPublisher;
import com.luispiquinrey.KnotCommerce.DTOs.MapperDTOs.MapperProductAndPayment;
import com.luispiquinrey.KnotCommerce.DTOs.ProductPaymentDTO;
import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Enums.Tactic;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;
import com.luispiquinrey.KnotCommerce.Service.FacadeServiceProduct;

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
    private final FacadeServiceProduct facadeServiceProduct;

    @Autowired
    private final RabbitMQPublisher rabbitMQPublisher;

    @Autowired
    private final MapperProductAndPayment mapperProductAndPayment;

    public RestControllerProduct(RabbitMQPublisher rabbitMQPublisher, MapperProductAndPayment mapperProductAndPayment, FacadeServiceProduct facadeServiceProduct){
        this.rabbitMQPublisher=rabbitMQPublisher;
        this.mapperProductAndPayment=mapperProductAndPayment;
        this.facadeServiceProduct=facadeServiceProduct;
    }

    @PostMapping("/buyProduct/{id}")
    public ResponseEntity<?> buyProduct(@PathVariable Long id) {
        try {
            if (!facadeServiceProduct.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with id " + id + " not found.");
            }
            Product product = facadeServiceProduct.getProductOrThrow(id);


            if (product.getStock() == null || product.getStock() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Product with id " + id + " is out of stock.");
            }

            product.setStock(product.getStock() - 1);

            facadeServiceProduct.updateProduct(product);

            return ResponseEntity.ok("Product with id " + id + " purchased successfully. Remaining stock: " + product.getStock());

        } catch (EntityNotFoundException e) {
            logger.error("Product not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product not found: " + e.getMessage());
        } catch (ProductUpdateException e) {
            logger.error("Error updating product stock: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error updating product stock: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error occurred: " + e.getMessage());
        }
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
            facadeServiceProduct.createProduct(product);
            ProductPaymentDTO paymentDTO=mapperProductAndPayment.toPaymentDTO(product);
            paymentDTO.setTactic(Tactic.CREATE_PRODUCT);
            rabbitMQPublisher.sendMessageStripe(paymentDTO);

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
            facadeServiceProduct.deleteProductById(id);

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
            facadeServiceProduct.updateProduct(product);

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
            Product product = facadeServiceProduct.getProductOrThrow(id);

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
            return ResponseEntity.ok(facadeServiceProduct.findAvailableProducts());
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
            return ResponseEntity.ok(facadeServiceProduct.findByCategoryName(categoryName));
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
            return ResponseEntity.ok(facadeServiceProduct.findByPriceRange(minPrice, maxPrice));
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
            facadeServiceProduct.deleteByCategory(category);
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
            facadeServiceProduct.updateStock(id, stock);
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
            return ResponseEntity.ok(facadeServiceProduct.findAllProducts());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving all products: " + e.getMessage());
        }
    }
}
