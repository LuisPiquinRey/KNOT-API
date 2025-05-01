package com.luispiquinrey.apiknot.RestController;


import com.luispiquinrey.apiknot.Entities.DTO.PurchaseRequest;
import com.luispiquinrey.apiknot.Entities.Product.ProductPackage.Product;
import com.luispiquinrey.apiknot.Entities.User;
import com.luispiquinrey.apiknot.Service.ImplServiceProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product")
public class RestControllerProduct {

    @Autowired
    private final ImplServiceProduct implServiceProduct;

    Logger logger = LoggerFactory.getLogger(RestControllerProduct.class);

    public RestControllerProduct(ImplServiceProduct implServiceProduct) {
        this.implServiceProduct = implServiceProduct;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(User user, @RequestBody Product product) {
        try{
            implServiceProduct.createProduct(product);
            logger.info("Product '{}' created successfully by user '{}'", product.toString(), "Created by: " + user.getUsername());
            return ResponseEntity.ok("Product created successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(User user, @PathVariable Integer id){
        try{
            implServiceProduct.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Error deleting product: " + e.getMessage());
        }
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findByIdProduct(@PathVariable Integer product_id) {
        try {
            Product product = implServiceProduct.findProductById(product_id);
            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(404).body("Product not found with ID: " + product_id);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving product: " + e.getMessage());
        }
    }
    @GetMapping("/seeAll")
    public ResponseEntity<?> seeAllProducts() {
        try {
            Iterable<Product> products = implServiceProduct.findAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving products: " + e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(User user, @RequestBody Product product) {
        try {
            Product updatedProduct = implServiceProduct.updateProduct(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating product: " + e.getMessage());
        }
    }
    @PostMapping("/buy")
    public ResponseEntity<?> buyProducts(@RequestBody PurchaseRequest purchaseRequest) {
        try {
            implServiceProduct.buyProduct(purchaseRequest.getItems());
            return ResponseEntity.ok("🛒 Purchase completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error during purchase: " + e.getMessage());
        }
    }
}
