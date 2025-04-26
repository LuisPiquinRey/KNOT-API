package com.luispiquinrey.apiknot.Repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.luispiquinrey.apiknot.Entities.Product;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class RepositoryProductTest {
    @Mock
    private RepositoryProductJpa repositoryProductJpa;

    private Product product;

    @Test
    @DisplayName("Create product")
    void createProduct() {
        product = new Product(null,1,"Adidas",10,12.4f,null);
        when(repositoryProductJpa.save(any(Product.class))).thenReturn(product);
        Product result = repositoryProductJpa.save(product);
        assertEquals(product, result);
    }
    @Test
    @DisplayName("Update product")
    void updateProduct() {
        Product existingProduct = new Product(null, 1, "Adidas", 10, 12.4f, null);
        Product updatedProduct = new Product(null, 1, "Nike", 15, 15.9f, null);
        when(repositoryProductJpa.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = repositoryProductJpa.save(updatedProduct);

        assertEquals(updatedProduct.getProduct_id(), result.getProduct_id());
        assertEquals("Nike", result.getBrand());
        assertEquals(15, result.getStock());
        assertEquals(15.9f, result.getPrice());
    }
    @Test
    @DisplayName("Find by Id")
    void findById() {
        product = new Product(null,1,"Adidas",10,12.4f,null);
        when(repositoryProductJpa.findById(any(Integer.class))).thenReturn(java.util.Optional.of(product));
        Product result = repositoryProductJpa.findById(product.getProduct_id()).get();
        assertEquals(product, result);
    }
    @Test
    @DisplayName("Delete product")
    void deleteProduct() {
        Product productSaved = new Product(null, 1, "Adidas", 10, 12.4f, null);
        when(repositoryProductJpa.existsById(productSaved.getProduct_id())).thenReturn(true);
        repositoryProductJpa.deleteById(productSaved.getProduct_id());
        when(repositoryProductJpa.existsById(productSaved.getProduct_id())).thenReturn(false);
        boolean exists = repositoryProductJpa.existsById(productSaved.getProduct_id());
        assertFalse(exists);
    }
}