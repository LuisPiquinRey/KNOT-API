package com.luispiquinrey.KnotCommerce.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryProduct;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class ImplServiceProduct implements IServiceProduct {

    @Autowired
    private final RepositoryProduct repositoryProduct;

    Logger logger = LoggerFactory.getLogger(ImplServiceProduct.class);

    ImplServiceProduct(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @Override
    public void deleteProductById(Long id_Product) {
        if (repositoryProduct.existsById(id_Product)) {
            repositoryProduct.deleteById(id_Product);
            System.out.println("\u001B[31m🗑️ Product deleted successfully. If this was unintentional, please contact support immediately.⚠️\u001B[0m");
        } else {
            logger.warn("❌ Cannot delete: Product with ID {} does not exist.", id_Product);
            System.out.println("\u001B[31m❌ Cannot delete: Product with ID " + id_Product + " does not exist. 🕵️‍♂️\u001B[0m");
        }
    }

    @Override
    public void updateProductById(Product product) {
        Long id = product.getId_Product();
        if (id == null) {
            logger.warn("❌ Cannot update: Product ID is null.");
            System.out.println("\u001B[31m❌ Cannot update: Product ID is null. 🕵️‍♂️\u001B[0m");
            return;
        }
        if (repositoryProduct.existsById(id)) {
            repositoryProduct.save(product);
            System.out.println("\u001B[32m✅ Product updated successfully! 🛠️\u001B[0m");
        } else {
            logger.warn("❌ Cannot update: Product with ID {} does not exist.", id);
            System.out.println("\u001B[31m❌ Cannot update: Product with ID " + id + " does not exist. 🕵️‍♂️\u001B[0m");
        }
    }

    @Override
    public void createProduct(Product product) {
        try {
            repositoryProduct.save(product);
            System.out.println("\u001B[32m✅ Product created successfully! 🎉\u001B[0m");
        } catch (Exception e) {
            logger.error("🚨 Failed to create product. Possible causes: invalid data, database issues, or internal errors. 🛑", e);
            System.out.println("\u001B[31m❌ Failed to create product. Please check the logs for more details. 🕵️‍♂️\u001B[0m");
        }
    }

    @Override
    public List<Product> findAvailableProducts() {
        try {
            List<Product> products = repositoryProduct.findAvailableProducts();
            System.out.println("\u001B[34m🔎 Found " + products.size() + " available products.\u001B[0m");
            return products;
        } catch (Exception e) {
            logger.error("🚨 Failed to retrieve available products. 🛑", e);
            System.out.println("\u001B[31m❌ Failed to retrieve available products. 🕵️‍♂️\u001B[0m");
            return List.of();
        }
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        try {
            List<Product> products = repositoryProduct.findByCategoryName(categoryName);
            System.out.println("\u001B[34m🔎 Found " + products.size() + " products in category '" + categoryName + "'.\u001B[0m");
            return products;
        } catch (Exception e) {
            logger.error("🚨 Failed to retrieve products by category '{}'. 🛑", categoryName, e);
            System.out.println("\u001B[31m❌ Failed to retrieve products by category. 🕵️‍♂️\u001B[0m");
            return List.of();
        }
    }

    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        try {
            List<Product> products = repositoryProduct.findByPriceRange(minPrice, maxPrice);
            System.out.println("\u001B[34m🔎 Found " + products.size() + " products in price range [" + minPrice + ", " + maxPrice + "].\u001B[0m");
            return products;
        } catch (Exception e) {
            logger.error("🚨 Failed to retrieve products by price range [{} - {}]. 🛑", minPrice, maxPrice, e);
            System.out.println("\u001B[31m❌ Failed to retrieve products by price range. 🕵️‍♂️\u001B[0m");
            return List.of();
        }
    }

    @Override
    public void deleteByCategory(Category category) {
        try {
            repositoryProduct.deleteByCategory(category);
            System.out.println("\u001B[31m🗑️ Products deleted by category successfully.⚠️\u001B[0m");
        } catch (Exception e) {
            logger.error("🚨 Failed to delete products by category. 🛑", e);
            System.out.println("\u001B[31m❌ Failed to delete products by category. 🕵️‍♂️\u001B[0m");
        }
    }

    @Override
    public void updateStock(Long id, int stock) {
        try {
            repositoryProduct.updateStock(id, stock);
            System.out.println("\u001B[32m✅ Stock updated successfully for product ID " + id + "! 📦\u001B[0m");
        } catch (Exception e) {
            logger.error("🚨 Failed to update stock for product ID {}. 🛑", id, e);
            System.out.println("\u001B[31m❌ Failed to update stock for product ID " + id + ". 🕵️‍♂️\u001B[0m");
        }
    }
    @Override
    public Product getProductOrThrow(Long id_Product) {
        return repositoryProduct.findById(id_Product)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id_Product + " not found."));
    }
    @Override
    public List<Product> findAllProducts() {
        try {
            List<Product> products = repositoryProduct.findAll();
            System.out.println("\u001B[34m🔎 Found " + products.size() + " products in total.\u001B[0m");
            return products;
            } catch (Exception e) {
                logger.error("🚨 Failed to retrieve all products. 🛑", e);
                System.out.println("\u001B[31m❌ Failed to retrieve all products. 🕵️‍♂️\u001B[0m");
                return List.of();
            }
        }
}