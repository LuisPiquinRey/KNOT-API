package com.luispiquinrey.KnotCommerce.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.NoPerishableProduct;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryProductTest {

    private Product testproduct;
    private Category testcategory;

    @Mock
    private RepositoryProduct repositoryProduct;

    @BeforeEach
    void setUp() {
        testproduct=new NoPerishableProduct();
        testproduct.setName("Adidas samba");
        testproduct.setPrice(120.32);
        testproduct.setStock(5);
        testproduct.setAvailable(true);

        testcategory=new Category();
        testcategory.setName("Sports");
        testcategory.setId_Category(1);
        testproduct.setCategories(List.of(testcategory));
    }
    @Test
    @DisplayName("Find product by id")
    public void findProductById(){
        Mockito.when(repositoryProduct.findById(1L)).thenReturn(Optional.of(testproduct));
        Optional<Product> result=repositoryProduct.findById(1L);
        assertTrue(result.isPresent(),"Product should be present");
        assertEquals("Adidas samba", result.get().getName(),"Product name should match");
        assertEquals(120.32, result.get().getPrice(),"Product price should match");
        assertEquals(5, result.get().getStock(),"Product stock should match");
        Mockito.verify(repositoryProduct, Mockito.times(1)).findById(1L);
    }
    @Test
    @DisplayName("Delete product by id")
    public void deleteProductById(){
        Mockito.doNothing().when(repositoryProduct).deleteById(1L);
        repositoryProduct.deleteById(1L);
        Mockito.when(repositoryProduct.findById(1L)).thenReturn(Optional.empty());
        Optional<Product> result = repositoryProduct.findById(1L);
        assertTrue(result.isEmpty());
    }
    @Test
    @DisplayName("Create product")
    public void createProduct(){
        Mockito.when(repositoryProduct.save(testproduct)).thenReturn(testproduct);
        Product result=repositoryProduct.save(testproduct);
        assertEquals("Adidas samba", result.getName(),"Product name should match");
        assertEquals(120.32, result.getPrice(),"Product price should match");
        assertEquals(5, result.getStock(),"Product stock should match");
        Mockito.verify(repositoryProduct, Mockito.times(1)).save(testproduct);
    }
    @Test
    @DisplayName("Find all products")
    public void findAllProducts() {
        List<Product> products = List.of(testproduct);
        Mockito.when(repositoryProduct.findAll()).thenReturn(products);

        List<Product> result = repositoryProduct.findAll();

        assertEquals(1, result.size(), "Should return one product");
        assertEquals("Adidas samba", result.get(0).getName(), "Product name should match");
        Mockito.verify(repositoryProduct, Mockito.times(1)).findAll();
    }
    @Test
    @DisplayName("Update product")
    public void updateProduct() {
        testproduct.setPrice(150.00);
        testproduct.setStock(10);

        Mockito.when(repositoryProduct.save(testproduct)).thenReturn(testproduct);

        Product updated = repositoryProduct.save(testproduct);

        assertEquals("Adidas samba", updated.getName(), "Product name should match");
        assertEquals(150.00, updated.getPrice(), "Product price should be updated");
        assertEquals(10, updated.getStock(), "Product stock should be updated");
        Mockito.verify(repositoryProduct, Mockito.times(1)).save(testproduct);
    }
    @Test
    @DisplayName("Find available products")
    public void findAvailableProducts() {
        List<Product> availableProducts = List.of(testproduct);
        Mockito.when(repositoryProduct.findAvailableProducts()).thenReturn(availableProducts);
        List<Product> result = repositoryProduct.findAvailableProducts();
        assertEquals(1, result.size(), "Should return one available product");
        assertEquals("Adidas samba", result.get(0).getName(), "Product name should match");
        assertTrue(result.get(0).getStock() > 0, "Product stock should be greater than 0");

        Mockito.verify(repositoryProduct, Mockito.times(1)).findAvailableProducts();
    }
    @Test
    @DisplayName("Find by category name")
    public void findByCategoryName() {
        Mockito.when(repositoryProduct.findByCategoryName("Sports")).thenReturn(List.of(testproduct));
        List<Product> products = repositoryProduct.findByCategoryName("Sports");

        assertEquals(1, products.size(), "Should return one product");
        assertEquals("Adidas samba", products.get(0).getName(), "Product name should match");
        assertTrue(products.get(0).getCategories().stream()
            .anyMatch(cat -> "Sports".equals(cat.getName())), "Product should have 'Sports' category");

        Mockito.verify(repositoryProduct, Mockito.times(1)).findByCategoryName("Sports");
    }
    @Test
    @DisplayName("Find products by lower stock")
    public void findLowStockProducts(){
        Mockito.when(repositoryProduct.findLowStockProducts(10)).thenReturn(List.of(testproduct));
        List<Product> products=repositoryProduct.findLowStockProducts(10);
        assertEquals(1,products.size(),"Should return one product");
        assertEquals("Adidas samba", products.get(0).getName(), "Product name should match");
        assertEquals(120.32, products.get(0).getPrice(),"Product price should match");
        assertEquals(5, products.get(0).getStock(),"Product stock should match");
    }
    @Test
    @DisplayName("Find products by price range")
    public void findByPriceRange() {
        Mockito.when(repositoryProduct.findByPriceRange(100.0, 130.0)).thenReturn(List.of(testproduct));
        List<Product> products = repositoryProduct.findByPriceRange(100.0, 130.0);

        assertEquals(1, products.size(), "Should return one product in price range");
        assertEquals("Adidas samba", products.get(0).getName(), "Product name should match");
        Mockito.verify(repositoryProduct, Mockito.times(1)).findByPriceRange(100.0, 130.0);
    }

    @Test
    @DisplayName("Delete products by category")
    public void deleteByCategory() {
        Mockito.doNothing().when(repositoryProduct).deleteByCategory(testcategory);
        repositoryProduct.deleteByCategory(testcategory);
        Mockito.verify(repositoryProduct, Mockito.times(1)).deleteByCategory(testcategory);
    }

    @Test
    @DisplayName("Update product stock")
    public void updateStock() {
        Mockito.doNothing().when(repositoryProduct).updateStock(1L, 20);
        repositoryProduct.updateStock(1L, 20);
        Mockito.verify(repositoryProduct, Mockito.times(1)).updateStock(1L, 20);
        }
}
