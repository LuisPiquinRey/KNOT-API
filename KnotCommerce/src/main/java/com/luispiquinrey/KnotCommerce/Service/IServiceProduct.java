package com.luispiquinrey.KnotCommerce.Service;

import java.util.List;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;

public interface IServiceProduct {
    public Product getProductOrThrow(Long id_Product);
    void deleteProductById(Long id_Product);
    void updateProductById(Product product);
    void createProduct(Product product);
    List<Product> findAvailableProducts();
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByPriceRange(double minPrice, double maxPrice);
    void deleteByCategory(Category category);
    void updateStock(Long id, int stock);
    List<Product> findAllProducts();
}
