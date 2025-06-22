package com.luispiquinrey.KnotCommerce.Service;

import java.util.List;

import org.hibernate.id.factory.IdGenFactoryLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luispiquinrey.KnotCommerce.DTOs.ProductNode;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryProductNode;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ImplServiceProductNode implements IServiceProductNode {

    @Autowired
    private final RepositoryProductNode repositoryProductNode;

    private static final Logger logger = LoggerFactory.getLogger(ImplServiceProductNode.class);

    public ImplServiceProductNode(RepositoryProductNode repositoryProductNode) {
        this.repositoryProductNode = repositoryProductNode;
    }

    @Override
    public ProductNode save(ProductNode productNode) {
        try {
            ProductNode saved = repositoryProductNode.save(productNode);
            logger.info("\u001B[32m🎉 [NODE CREATED] ➤ ProductNode created/updated successfully: {}\u001B[0m", saved);
            return saved;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [SAVE FAILED] ➤ Error saving ProductNode: {}\u001B[0m", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<ProductNode> findAll() {
        try {
            List<ProductNode> nodes = (List<ProductNode>) repositoryProductNode.findAll();
            logger.info("\u001B[34m🔍 [NODES RETRIEVED] ➤ Found {} ProductNodes.\u001B[0m", nodes.size());
            return nodes;
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [RETRIEVE FAILED] ➤ Error retrieving ProductNodes.\u001B[0m", e);
            return List.of();
        }
    }

    @Override
    public ProductNode getProductNodeOrThrow(Long id_Product) throws EntityNotFoundException {
        try {
            return repositoryProductNode.findById(id_Product)
                .map(node -> {
                    logger.info("\u001B[32m🔍 [NODE FOUND] ➤ ProductNode with ID {} found.\u001B[0m", id_Product);
                    return node;
                })
                .orElseThrow(() -> {
                    logger.warn("\u001B[33m❌ [NOT FOUND] ➤ ProductNode with ID {} not found.\u001B[0m", id_Product);
                    return new EntityNotFoundException("ProductNode with ID " + id_Product + " not found.");
                });
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [FIND FAILED] ➤ Error finding ProductNode with ID {}.\u001B[0m", id_Product, e);
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            if (repositoryProductNode.existsById(id)) {
                repositoryProductNode.deleteById(id);
                logger.info("\u001B[31m🗑️ [NODE DELETED] ➤ ProductNode with ID {} deleted successfully.\u001B[0m", id);
            } else {
                logger.warn("\u001B[33m❌ [DELETE FAILED] ➤ ProductNode with ID {} does not exist.\u001B[0m", id);
            }
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [DELETE FAILED] ➤ Error deleting ProductNode with ID {}.\u001B[0m", id, e);
        }
    }
}

