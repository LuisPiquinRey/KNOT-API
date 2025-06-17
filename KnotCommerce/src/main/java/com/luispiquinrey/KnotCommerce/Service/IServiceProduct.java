package com.luispiquinrey.KnotCommerce.Service;

import java.util.List;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;

import jakarta.persistence.EntityNotFoundException;

public interface IServiceProduct {
    public Product getProductOrThrow(Long id_Product) throws EntityNotFoundException;
    void deleteProductById(Long id_Product) throws ProductDeleteException;
    void updateProduct (Product product) throws ProductUpdateException;
    void createProduct(Product product) throws ProductCreationException;
    List<Product> findAvailableProducts();
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByPriceRange(double minPrice, double maxPrice);
    void deleteByCategory(Category category);
    void updateStock(Long id, int stock);
    List<Product> findAllProducts();
}
