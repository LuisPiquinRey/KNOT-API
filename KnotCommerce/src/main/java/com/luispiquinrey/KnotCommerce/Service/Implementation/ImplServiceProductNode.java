package com.luispiquinrey.KnotCommerce.Service.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.KnotCommerce.DTOs.CategoryNode;
import com.luispiquinrey.KnotCommerce.DTOs.ProductNode;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductNodeCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductNodeDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductNodeUpdateException;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryProductNode;
import com.luispiquinrey.common_tools.Service.ICrudService;



@Service
public class ImplServiceProductNode implements ICrudService<Product, Long>{

    @Autowired
    private final RepositoryProductNode repositoryProductNode;

    private static final Logger logger = LoggerFactory.getLogger(ImplServiceProductNode.class);

    public ImplServiceProductNode(RepositoryProductNode repositoryProductNode) {
        this.repositoryProductNode = repositoryProductNode;
    }

    public ProductNode productToProductNode(Product product){
        List<CategoryNode> categoryNodes = null;
        if (product.getCategories() != null) {
            categoryNodes = product.getCategories().stream()
                .map(cat -> new CategoryNode(cat.getId_Category(), cat.getName()))
                .collect(Collectors.toList());
        }
        return new ProductNode(product.getId_Product(), categoryNodes);
    }

    @Transactional
    @Override
    public void createTarget(Product product) throws ProductNodeCreationException {
        try {
            ProductNode node = productToProductNode(product);
            repositoryProductNode.save(node);
            logger.info("\u001B[32m🎉 [NODE CREATED] ➤ ProductNode created successfully: {}\u001B[0m", node);
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [CREATE NODE FAILED] ➤ Error creating ProductNode for product ID {}: {}\u001B[0m", product.getId_Product(), e.getMessage(), e);
            throw new ProductNodeCreationException("Error creating ProductNode: " + e.getMessage(), product.getId_Product());
        }
    }

    @Transactional
    @Override
    public void updateTarget(Product product) throws ProductNodeUpdateException {
        Long id = product.getId_Product();
        try {
            if (repositoryProductNode.existsById(id)) {
                ProductNode updatedNode = productToProductNode(product);
                repositoryProductNode.save(updatedNode);
                logger.info("\u001B[36m🛠️ [NODE UPDATED] ➤ ProductNode with ID {} updated successfully.\u001B[0m", id);
            } else {
                logger.warn("\u001B[33m❌ [UPDATE NODE FAILED] ➤ ProductNode with ID {} does not exist.\u001B[0m", id);
                throw new ProductNodeUpdateException("ProductNode with ID " + id + " does not exist.", id);
            }
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [UPDATE NODE FAILED] ➤ Error updating ProductNode with ID {}: {}\u001B[0m", id, e.getMessage(), e);
            throw new ProductNodeUpdateException("Error updating ProductNode: " + e.getMessage(), id);
        }
    }

    @Transactional
    @Override
    public void deleteTargetById(Long id_Product) throws ProductNodeDeleteException {
        try {
            if (repositoryProductNode.existsById(id_Product)) {
                repositoryProductNode.deleteById(id_Product);
                logger.info("\u001B[31m🗑️ [NODE DELETED] ➤ ProductNode with ID {} deleted successfully.\u001B[0m", id_Product);
            } else {
                logger.warn("\u001B[33m❌ [DELETE NODE FAILED] ➤ ProductNode with ID {} does not exist.\u001B[0m", id_Product);
                throw new ProductNodeDeleteException("ProductNode with ID " + id_Product + " does not exist.", id_Product);
            }
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [DELETE NODE FAILED] ➤ Error deleting ProductNode with ID {}: {}\u001B[0m", id_Product, e.getMessage(), e);
            throw new ProductNodeDeleteException("Error deleting ProductNode: " + e.getMessage(), id_Product);
        }
    }
}

