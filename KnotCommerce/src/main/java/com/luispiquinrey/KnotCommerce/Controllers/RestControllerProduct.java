package com.luispiquinrey.KnotCommerce.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;
import com.luispiquinrey.KnotCommerce.Service.IServiceProduct;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class RestControllerProduct {

    @Autowired
    private final IServiceProduct iServiceProduct;

    public RestControllerProduct(IServiceProduct iServiceProduct){
        this.iServiceProduct = iServiceProduct;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            iServiceProduct.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product created successfully!");
        } catch (ProductCreationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        try{
            iServiceProduct.deleteProductById(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body("Product with the id: " + id + " correctly deleted");
        }catch(ProductDeleteException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        try{
            iServiceProduct.updateProduct(product);
            return ResponseEntity.status(HttpStatus.OK)
                .body("Product with id: " + product.getId_Product() + " correctly updated");
        }catch(ProductUpdateException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try{
            Product product = iServiceProduct.getProductOrThrow(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(product.productToJson());
        }catch(EntityNotFoundException | JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @GetMapping("/availableProducts")
    public ResponseEntity<?> getAvailableProducts() {
        try {
            return ResponseEntity.ok(iServiceProduct.findAvailableProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving available products: " + e.getMessage());
        }
    }

    @GetMapping("/productsByCategory/{categoryName}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String categoryName) {
        try {
            return ResponseEntity.ok(iServiceProduct.findByCategoryName(categoryName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving products by category: " + e.getMessage());
        }
    }

    @GetMapping("/productsByPriceRange")
    public ResponseEntity<?> getProductsByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        try {
            return ResponseEntity.ok(iServiceProduct.findByPriceRange(minPrice, maxPrice));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving products by price range: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteByCategory")
    public ResponseEntity<?> deleteByCategory(@RequestBody Category category) {
        try {
            iServiceProduct.deleteByCategory(category);
            return ResponseEntity.ok("Products in category '" + category.getName() + "' deleted successfully!");
        } catch (ProductDeleteException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @PutMapping("/updateStock/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam int stock) {
        try {
            iServiceProduct.updateStock(id, stock);
            return ResponseEntity.ok("Stock updated for product with id: " + id);
        } catch (ProductUpdateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }

    @GetMapping("/findAllProducts")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(iServiceProduct.findAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving all products: " + e.getMessage());
        }
    }
}