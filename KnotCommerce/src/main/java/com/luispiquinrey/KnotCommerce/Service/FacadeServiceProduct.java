package com.luispiquinrey.KnotCommerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void createProduct(Product product) {
        implServiceProduct.createProduct(product);
        implServiceProductNode.createProduct(product);
    }

    @Override
    public Product getProductOrThrow(Long id_Product) throws EntityNotFoundException {
        return implServiceProduct.getProductOrThrow(id_Product);
    }

    @Override
    public void deleteProductById(Long id_Product) throws ProductDeleteException {
        implServiceProduct.deleteProductById(id_Product);
        implServiceProductNode.deleteProductById(id_Product);
    }

    @Override
    public void updateProduct(Product product) throws ProductUpdateException {
        implServiceProduct.updateProduct(product);
        implServiceProductNode.updateProduct(product);
    }

    @Override
    public List<Product> findAvailableProducts() {
        return implServiceProduct.findAvailableProducts();
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return implServiceProduct.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return implServiceProduct.findByPriceRange(minPrice, maxPrice);
    }

    @Override
    public void deleteByCategory(Category category) {
        implServiceProduct.deleteByCategory(category);
    }

    @Override
    public void updateStock(Long id, int stock) {
        implServiceProduct.updateStock(id, stock);
    }

    @Override
    public List<Product> findAllProducts() {
        return implServiceProduct.findAllProducts();
    }

    @Override
    public boolean existsById(Long id_Product) {
        return implServiceProduct.existsById(id_Product);
    }
}
