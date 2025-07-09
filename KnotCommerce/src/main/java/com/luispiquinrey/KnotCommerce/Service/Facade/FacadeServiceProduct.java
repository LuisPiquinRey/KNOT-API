package com.luispiquinrey.KnotCommerce.Service.Facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;
import com.luispiquinrey.KnotCommerce.Service.Implementation.ImplServiceProduct;
import com.luispiquinrey.KnotCommerce.Service.Implementation.ImplServiceProductNode;
import com.luispiquinrey.KnotCommerce.Service.Interface.IServiceProduct;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FacadeServiceProduct implements IServiceProduct {

    @Autowired
    private final ImplServiceProduct implServiceProduct;

    @Autowired
    private final ImplServiceProductNode implServiceProductNode;

    public FacadeServiceProduct(ImplServiceProduct implServiceProduct, ImplServiceProductNode implServiceProductNode) {
        this.implServiceProduct = implServiceProduct;
        this.implServiceProductNode = implServiceProductNode;
    }

    @Override
    @CachePut(value = "product", key = "#product.id_Product",unless="#result==null")
    public void createTarget(Product product) {
        implServiceProduct.createTarget(product);
        implServiceProductNode.createTarget(product);
    }

    @Override
    @Cacheable(value = "product", key = "#id_Product")
    public Product getProductOrThrow(Long id_Product) throws EntityNotFoundException {
        return implServiceProduct.getProductOrThrow(id_Product);
    }

    @Override
    @CacheEvict(value = "product", key = "#id_Product")
    public void deleteTargetById(Long id_Product) throws ProductDeleteException {
        implServiceProduct.deleteTargetById(id_Product);
        implServiceProductNode.deleteTargetById(id_Product);
    }

    @Override
    @CachePut(value = "product", key = "#product.id_Product")
    public void updateTarget(Product product) throws ProductUpdateException {
        implServiceProduct.updateTarget(product);
        implServiceProductNode.updateTarget(product);
    }

    @Override
    @Cacheable("availableProducts")
    public List<Product> findAvailableProducts() {
        return implServiceProduct.findAvailableProducts();
    }

    @Override
    @Cacheable(value = "productsByCategory", key = "#categoryName")
    public List<Product> findByCategoryName(String categoryName) {
        return implServiceProduct.findByCategoryName(categoryName);
    }

    @Override
    @Cacheable(value = "productsByPriceRange", key = "{#minPrice, #maxPrice}")
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return implServiceProduct.findByPriceRange(minPrice, maxPrice);
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public void deleteByCategory(Category category) {
        implServiceProduct.deleteByCategory(category);
    }

    @Override
    @CachePut(value = "product", key = "#id",unless="#result==null")
    public void updateStock(Long id, int stock) {
        implServiceProduct.updateStock(id, stock);
    }

    @Override
    @Cacheable("products")
    public List<Product> findAllProducts() {
        return implServiceProduct.findAllProducts();
    }

    @Override
    public boolean existsById(Long id_Product) {
        return implServiceProduct.existsById(id_Product);
    }

    @Override
    public List<Product> findAll(Pageable pageable) {
        return implServiceProduct.findAll(pageable);
    }
}
