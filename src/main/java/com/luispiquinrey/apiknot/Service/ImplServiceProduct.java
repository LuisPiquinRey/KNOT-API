package com.luispiquinrey.apiknot.Service;

import java.util.List;
import java.util.Objects;


import com.luispiquinrey.apiknot.Entities.DTO.PurchaseItem;
import com.luispiquinrey.apiknot.Entities.Product;
import com.luispiquinrey.apiknot.Repository.RepositoryProductJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ImplServiceProduct implements IServiceProduct {

    @Autowired
    private final RepositoryProductJpa repositoryProduct;

    public ImplServiceProduct(RepositoryProductJpa
                                      repositoryProduct) {
        this.repositoryProduct = Objects.requireNonNull(repositoryProduct, "RepositoryProduct cannot be null");
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        if (repositoryProduct.existsById(product.getProduct_id())) {
            throw new RuntimeException("Product with ID " + product.getProduct_id() + " already exists");
        }
        return repositoryProduct.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        Objects.requireNonNull(product.getProduct_id(), "Product ID cannot be null");

        if (!repositoryProduct.existsById(product.getProduct_id())) {
            throw new RuntimeException("Product not found to update");
        }
        return repositoryProduct.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer product_id) {
        if (product_id == null) {
            return;
        }
        if (repositoryProduct.existsById(product_id)) {
            repositoryProduct.deleteById(product_id);
        }
    }

    @Override
    @Transactional
    public Product findProductById(Integer product_id) {
        Objects.requireNonNull(product_id, "Product ID cannot be null");

        return repositoryProduct.findById(product_id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + product_id + " not found"));
    }

    @Override
    @Transactional
    public Iterable<Product> findAllProducts() {
        return repositoryProduct.findAll();
    }
    public void buyProduct(List<PurchaseItem> items) {
        for (PurchaseItem item : items) {
            try {
                Product product = findProductById(item.getProductId());
                int newStock = product.getStock() - item.getQuantity();
                if (newStock <= 0) {
                    deleteProduct(item.getProductId());
                    System.out.println("🗑️ Product with ID " + item.getProductId() + " has been deleted due to insufficient stock.");
                } else {
                    product.setStock(newStock);
                    updateProduct(product);
                    System.out.println("✅ Bought " + item.getQuantity() + " of: " + product.getProduct_id());
                }
            } catch (Exception e) {
                System.err.println("⚠️ Error processing product ID " + item.getProductId() + ": " + e.getMessage());
            }
        }
    }
}