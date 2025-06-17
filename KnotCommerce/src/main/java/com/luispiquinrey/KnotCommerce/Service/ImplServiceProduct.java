package com.luispiquinrey.KnotCommerce.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryProduct;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ImplServiceProduct implements IServiceProduct {

    @Autowired
    private final RepositoryProduct repositoryProduct;

    Logger logger = LoggerFactory.getLogger(ImplServiceProduct.class);

    ImplServiceProduct(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @Transactional
    @Override
    public void deleteProductById(Long id_Product) {
        if (repositoryProduct.existsById(id_Product)) {
            repositoryProduct.deleteById(id_Product);
            logger.info("\u001B[31m🗑️ [PRODUCT DELETED] ➤ Product with ID {} was deleted successfully.\u001B[0m", id_Product);
        } else {
            logger.warn("\u001B[33m❌ [DELETE FAILED] ➤ Product with ID {} does not exist.\u001B[0m", id_Product);
            throw new ProductDeleteException("Error deleting product: Product with ID " + id_Product + " does not exist.", id_Product);
        }
    }

    @Transactional
    @Override
    public void updateProduct(Product product) throws ProductUpdateException {
        Long id = product.getId_Product();
        if (repositoryProduct.existsById(id)) {
            repositoryProduct.save(product);
            logger.info("\u001B[36m🛠️ [PRODUCT UPDATED] ➤ Product with ID {} updated successfully.\u001B[0m", id);
        } else {
            logger.warn("\u001B[33m❌ [UPDATE FAILED] ➤ Product with ID {} does not exist.\u001B[0m", id);
            throw new ProductUpdateException("Error updating product", id);
        }
    }

    @Transactional
    @Override
    public void createProduct(Product product) throws ProductCreationException {
        try {
            repositoryProduct.save(product);
            logger.info("\u001B[32m🎉 [PRODUCT CREATED] ➤ Product created successfully:\n{}\u001B[0m", product);
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [CREATE FAILED] ➤ Error creating product with ID {}: {}\u001B[0m", product.getId_Product(), e.getMessage(), e);
            throw new ProductCreationException("Error creating product: " + e.getMessage(), product.getId_Product());
        }
    }

    @Override
    public List<Product> findAvailableProducts() {
        try {
            List<Product> products = repositoryProduct.findAvailableProducts();
            logger.info("\u001B[34m🔍 [AVAILABLE PRODUCTS] ➤ Found {} available products.\u001B[0m", products.size());
            return products;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [RETRIEVE FAILED] ➤ Error retrieving available products.\u001B[0m", e);
            return List.of();
        }
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        try {
            List<Product> products = repositoryProduct.findByCategoryName(categoryName);
            logger.info("\u001B[34m📂 [CATEGORY SEARCH] ➤ Found {} products in category '{}'.\u001B[0m", products.size(), categoryName);
            return products;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [CATEGORY FAILED] ➤ Error retrieving products in category '{}'.\u001B[0m", categoryName, e);
            return List.of();
        }
    }

    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        try {
            List<Product> products = repositoryProduct.findByPriceRange(minPrice, maxPrice);
            logger.info("\u001B[34m💰 [PRICE RANGE] ➤ Found {} products in range [{} - {}].\u001B[0m", products.size(), minPrice, maxPrice);
            return products;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [PRICE SEARCH FAILED] ➤ Error retrieving products in price range [{} - {}].\u001B[0m", minPrice, maxPrice, e);
            return List.of();
        }
    }

    @Transactional
    @Override
    public void deleteByCategory(Category category) {
        try {
            repositoryProduct.deleteByCategory(category);
            logger.info("\u001B[31m🧹 [CATEGORY DELETE] ➤ Products in category deleted successfully.\u001B[0m");
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [CATEGORY DELETE FAILED] ➤ Error deleting products by category.\u001B[0m", e);
        }
    }

    @Transactional
    @Override
    public void updateStock(Long id, int stock) {
        try {
            repositoryProduct.updateStock(id, stock);
            logger.info("\u001B[35m📦 [STOCK UPDATED] ➤ Product ID {} stock updated to {}.\u001B[0m", id, stock);
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [STOCK UPDATE FAILED] ➤ Could not update stock for product ID {}.\u001B[0m", id, e);
        }
    }

    @Override
    public Product getProductOrThrow(Long id_Product) throws EntityNotFoundException {
        return repositoryProduct.findById(id_Product)
            .map(product -> {
                logger.info("\u001B[32m🔍 [PRODUCT FOUND] ➤ Product with ID {} retrieved successfully.\u001B[0m", id_Product);
                return product;
            })
            .orElseThrow(() -> {
                logger.warn("\u001B[33m❌ [NOT FOUND] ➤ Product with ID {} was not found in the database.\u001B[0m", id_Product);
                return new EntityNotFoundException("Product with ID " + id_Product + " not found.");
            });
    }


    @Override
    public List<Product> findAllProducts() {
        try {
            List<Product> products = repositoryProduct.findAll();
            logger.info("\u001B[34m📦 [ALL PRODUCTS] ➤ Retrieved {} total products.\u001B[0m", products.size());
            return products;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [RETRIEVE FAILED] ➤ Error retrieving all products.\u001B[0m", e);
            return List.of();
        }
    }
}

