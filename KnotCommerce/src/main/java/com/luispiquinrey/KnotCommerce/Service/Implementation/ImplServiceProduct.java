package com.luispiquinrey.KnotCommerce.Service.Implementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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

@Service
public class ImplServiceProduct implements IServiceProduct {

    @Autowired
    private final RepositoryProduct repositoryProduct;

    @Autowired
    private final CacheManager cacheManager;

    private static final Logger logger = LoggerFactory.getLogger(ImplServiceProduct.class);

    public ImplServiceProduct(RepositoryProduct repositoryProduct, CacheManager cacheManager) {
        this.repositoryProduct = repositoryProduct;
        this.cacheManager = cacheManager;
    }

    @Transactional
    @Override
    @CacheEvict(value = "products", key = "#id_Product")
    public void deleteTargetById(Long id_Product) {
        if (repositoryProduct.existsById(id_Product)) {
            repositoryProduct.deleteById(id_Product);
            logger.info("\u001B[31m🗑️ [PRODUCT DELETED] ➤ Product with ID {} was deleted successfully.\u001B[0m",
                    id_Product);
        } else {
            logger.warn("\u001B[33m❌ [DELETE FAILED] ➤ Product with ID {} does not exist.\u001B[0m", id_Product);
            throw new ProductDeleteException(
                    "Error deleting product: Product with ID " + id_Product + " does not exist.", id_Product);
        }
    }

    @Transactional
    @Override
    @CachePut(value = "products", key = "#product.id_Product")
    public void updateTarget(Product product) throws ProductUpdateException {
        Long id = product.getId_Product();
        Product managed = repositoryProduct.findById(id)
                .orElseThrow(() -> new ProductUpdateException("Error updating product: Product with ID " + id + " does not exist.", id));


        managed.setName(product.getName());
        managed.setPrice(product.getPrice());
        managed.setDescription(product.getDescription());
        managed.setStock(product.getStock());
        managed.setAvailable(product.isAvailable());
        managed.setCategories(product.getCategories());
        managed.setCode_User(product.getCode_User());

        repositoryProduct.save(managed);
        logger.info("\u001B[36m🛠️ [PRODUCT UPDATED] ➤ Product with ID {} updated successfully.\u001B[0m", id);
    }

    /*
     * ⚠️ WARNING! Remember that product IDs are generated automatically, so you
     * need
     * to be careful with this implementation to ensure that Redis caches the ID
     * only
     * after the product is created — otherwise, it may cause issues.
     */
    @Transactional
    @Override
    public void createTarget(Product product) throws ProductCreationException {
        try {
            Product saved = repositoryProduct.save(product);
            logger.info("\u001B[32m🎉 [PRODUCT CREATED] ➤ Product created successfully:\n{}\u001B[0m", product);
            cacheManager.getCache("products").put(saved.getId_Product(), saved);
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [CREATE FAILED] ➤ Error creating product with ID {}: {}\u001B[0m",
                    product.getId_Product(), e.getMessage(), e);
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
            logger.info("\u001B[34m📂 [CATEGORY SEARCH] ➤ Found {} products in category '{}'.\u001B[0m",
                    products.size(), categoryName);
            return products;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [CATEGORY FAILED] ➤ Error retrieving products in category '{}'.\u001B[0m",
                    categoryName, e);
            return List.of();
        }
    }

    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        try {
            List<Product> products = repositoryProduct.findByPriceRange(minPrice, maxPrice);
            logger.info("\u001B[34m💰 [PRICE RANGE] ➤ Found {} products in range [{} - {}].\u001B[0m", products.size(),
                    minPrice, maxPrice);
            return products;
        } catch (Exception e) {
            logger.error(
                    "\u001B[31m🚨 [PRICE SEARCH FAILED] ➤ Error retrieving products in price range [{} - {}].\u001B[0m",
                    minPrice, maxPrice, e);
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
            logger.error("\u001B[31m🚨 [STOCK UPDATE FAILED] ➤ Could not update stock for product ID {}.\u001B[0m", id,
                    e);
        }
    }

    @Override
    @Cacheable(value = "products", key = "#id_Product", unless = "#result == null")
    public Product getProductOrThrow(Long id_Product) throws EntityNotFoundException {
        return repositoryProduct.findById(id_Product)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id_Product + " not found."));
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
            logger.error("\u001B[31m🚨 [EXISTS CHECK FAILED] ➤ Could not check existence for product ID {}.\u001B[0m",
                    id_Product, e);
            return false;
        }
    }
    public List<Product> findAll(Pageable pageable) {
    try {
        Page<Product> page = repositoryProduct.findAll(pageable);
        List<Product> products = page.getContent();
        logger.info("\u001B[34m📦 [PAGINATED PRODUCTS] ➤ Retrieved {} products from page {} of {}.\u001B[0m", 
                products.size(), page.getNumber() + 1, page.getTotalPages());
        return products;
    } catch (Exception e) {
        logger.error("\u001B[31m🚨 [RETRIEVE FAILED] ➤ Error retrieving paginated products.\u001B[0m", e);
        return List.of();
    }
}
}
