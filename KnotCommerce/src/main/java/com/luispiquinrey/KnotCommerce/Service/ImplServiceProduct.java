package com.luispiquinrey.KnotCommerce.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryProduct;
import com.luispiquinrey.KnotCommerce.Service.Interface.IServiceProduct;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ImplServiceProduct implements IServiceProduct {

    @Autowired
    private final RepositoryProduct repositoryProduct;
    private static final Logger logger = LoggerFactory.getLogger(ImplServiceProduct.class);

    public ImplServiceProduct(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @Transactional
    @Override
    @CacheEvict(value = "products", key = "#id_Product")
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
    @CachePut(value = "products", key = "#product.id_Product")
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
    @CachePut(value = "products", key = "#product.id_Product")
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
    @CacheEvict(value = "products", allEntries = true)
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
    @CacheEvict(value = "products", key = "#id")
    public void updateStock(Long id, int stock) {
        try {
            repositoryProduct.updateStock(id, stock);
            logger.info("\u001B[35m📦 [STOCK UPDATED] ➤ Product ID {} stock updated to {}.\u001B[0m", id, stock);
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [STOCK UPDATE FAILED] ➤ Could not update stock for product ID {}.\u001B[0m", id, e);
        }
    }

    @Override
    @Cacheable(value = "products", key = "#id_Product")
    public Product getProductOrThrow(Long id_Product) throws EntityNotFoundException {
        return repositoryProduct.findById(id_Product)
            .map(product -> {
                logger.info("\u001B[32m🔍 [PRODUCT FOUND - DB] ➤ Product with ID {} retrieved successfully.\u001B[0m", id_Product);
                return product;
            })
            .orElseThrow(() -> {
                logger.warn("\u001B[33m❌ [NOT FOUND] ➤ Product with ID {} was not found.\u001B[0m", id_Product);
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

    @Override
    public boolean existsById(Long id_Product) {
        try {
            boolean exists = repositoryProduct.existsById(id_Product);
            logger.info("\u001B[34m🔍 [EXISTS CHECK] ➤ Product with ID {} existence: {}.\u001B[0m", id_Product, exists);
            return exists;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [EXISTS CHECK FAILED] ➤ Could not check existence for product ID {}.\u001B[0m", id_Product, e);
            return false;
        }
    }
}
