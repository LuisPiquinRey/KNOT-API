package com.luispiquinrey.apiknot.Repository;

import com.luispiquinrey.apiknot.Entities.Product.ProductPackage.PerishableProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.luispiquinrey.apiknot.Entities.Product.ProductPackage.Product;

import java.time.LocalDate;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class RepositoryProductTest {
    @Mock
    private RepositoryProductJpa repositoryProductJpa;

    private PerishableProduct product;

    @Test
    @DisplayName("Create product")
    void createProduct() {
        product = new PerishableProduct.PerishableProductBuilder()
                .product_id(1)
                .brand("Adidas")
                .stock(10)
                .price(12.4f)
                .categories(null)
                .expirationDate(LocalDate.now().plusDays(30))
                .storageTemperature(4.0)
                .build();

        when(repositoryProductJpa.save(any(Product.class))).thenReturn(product);
        Product result = repositoryProductJpa.save(product);
        assertEquals(product, result);
    }

    @Test
    @DisplayName("Update product")
    void updateProduct() {
        PerishableProduct existingProduct = new PerishableProduct.PerishableProductBuilder()
                .product_id(1)
                .brand("Adidas")
                .stock(10)
                .price(12.4f)
                .categories(null)
                .expirationDate(LocalDate.now().plusDays(30))
                .storageTemperature(4.0)
                .build();

        PerishableProduct updatedProduct = new PerishableProduct.PerishableProductBuilder()
                .product_id(1)
                .brand("Nike")
                .stock(15)
                .price(15.9f)
                .categories(null)
                .expirationDate(LocalDate.now().plusDays(45))
                .storageTemperature(3.5)
                .build();

        when(repositoryProductJpa.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = repositoryProductJpa.save(updatedProduct);

        assertEquals(updatedProduct.getProduct_id(), result.getProduct_id());
        assertEquals("Nike", result.getBrand());
        assertEquals(15, result.getStock());
        assertEquals(15.9f, result.getPrice());
        assertEquals(updatedProduct.getExpirationDate(), ((PerishableProduct)result).getExpirationDate());
        assertEquals(updatedProduct.getStorageTemperature(), ((PerishableProduct)result).getStorageTemperature());
    }

    @Test
    @DisplayName("Find product by ID")
    void findById() {
        // Given
        product = new PerishableProduct.PerishableProductBuilder()
                .product_id(1)
                .brand("Adidas")
                .stock(10)
                .price(12.4f)
                .categories(null)
                .expirationDate(LocalDate.now().plusDays(30))
                .storageTemperature(4.0)
                .build();

        when(repositoryProductJpa.findById(1)).thenReturn(java.util.Optional.of(product));

        var optionalResult = repositoryProductJpa.findById(1);

        assertTrue(optionalResult.isPresent());
        assertEquals(product, optionalResult.get());
    }

    @Test
    @DisplayName("Delete product by ID")
    void deleteProduct() {

        int productId = 1;
        when(repositoryProductJpa.existsById(productId)).thenReturn(true) // antes de borrar
                .thenReturn(false); // después de borrar

        boolean existsBefore = repositoryProductJpa.existsById(productId);
        repositoryProductJpa.deleteById(productId);
        boolean existsAfter = repositoryProductJpa.existsById(productId);

        assertTrue(existsBefore);
        assertFalse(existsAfter);
    }

}