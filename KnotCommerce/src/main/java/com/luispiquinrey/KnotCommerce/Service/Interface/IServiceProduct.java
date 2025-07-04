package com.luispiquinrey.KnotCommerce.Service.Interface;

import java.util.List;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.common_tools.Service.ICrudService;

import jakarta.persistence.EntityNotFoundException;


public interface IServiceProduct extends ICrudService<Product, Long>{
    public Product getProductOrThrow(Long id_Product) throws EntityNotFoundException;
    List<Product> findAvailableProducts();
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByPriceRange(double minPrice, double maxPrice);
    void deleteByCategory(Category category);
    void updateStock(Long id, int stock);
    List<Product> findAllProducts();
    boolean existsById(Long id_Product);
}
