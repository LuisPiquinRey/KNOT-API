package com.luispiquinrey.KnotCommerce.Service.Implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.KnotCommerce.DTOs.CategoryNode;
import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryCategoryNode;
import com.luispiquinrey.KnotCommerce.Service.Interface.ICrudService;

@Service
public class ImplServiceCategoryNode implements ICrudService<Category, Integer> {

    @Autowired
    private final RepositoryCategoryNode repositoryCategoryNode;

    private static final Logger logger = LoggerFactory.getLogger(ImplServiceCategoryNode.class);

    public ImplServiceCategoryNode(RepositoryCategoryNode repositoryCategoryNode) {
        this.repositoryCategoryNode = repositoryCategoryNode;
    }

    public com.luispiquinrey.KnotCommerce.DTOs.CategoryNode categoryToCategoryNode(Category category) {
        return new CategoryNode(category.getId_Category(), category.getName());
    }

    @Transactional
    @Override
    public void createTarget(Category category) {
        try {
            CategoryNode node = categoryToCategoryNode(category);
            repositoryCategoryNode.save(node);
            logger.info("\u001B[32m🎉 [CATEGORY NODE CREATED] ➤ CategoryNode created successfully: {}\u001B[0m", node);
        } catch (Exception e) {
            logger.error(
                    "\u001B[31m🚨 [CREATE NODE FAILED] ➤ Error creating CategoryNode for category ID {}: {}\u001B[0m",
                    category.getId_Category(), e.getMessage(), e);
            throw new RuntimeException("Error creating CategoryNode: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void updateTarget(Category category) {
        Integer id = category.getId_Category();
        try {
            if (repositoryCategoryNode.existsById(id)) {
                CategoryNode node = categoryToCategoryNode(category);
                repositoryCategoryNode.save(node);
                logger.info(
                        "\u001B[36m🛠️ [CATEGORY NODE UPDATED] ➤ CategoryNode with ID {} updated successfully.\u001B[0m",
                        id);
            } else {
                logger.warn("\u001B[33m❌ [UPDATE NODE FAILED] ➤ CategoryNode with ID {} does not exist.\u001B[0m", id);
                throw new RuntimeException("CategoryNode with ID " + id + " does not exist.");
            }
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [UPDATE NODE FAILED] ➤ Error updating CategoryNode with ID {}: {}\u001B[0m", id,
                    e.getMessage(), e);
            throw new RuntimeException("Error updating CategoryNode: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteTargetById(Integer id_obj) {
        try {
            if (repositoryCategoryNode.existsById(id_obj)) {
                repositoryCategoryNode.deleteById(id_obj);
                logger.info(
                        "\u001B[31m🗑️ [CATEGORY NODE DELETED] ➤ CategoryNode with ID {} deleted successfully.\u001B[0m",
                        id_obj);
            } else {
                logger.warn("\u001B[33m❌ [DELETE NODE FAILED] ➤ CategoryNode with ID {} does not exist.\u001B[0m",
                        id_obj);
                throw new RuntimeException("CategoryNode with ID " + id_obj + " does not exist.");
            }
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [DELETE NODE FAILED] ➤ Error deleting CategoryNode with ID {}: {}\u001B[0m",
                    id_obj, e.getMessage(), e);
            throw new RuntimeException("Error deleting CategoryNode: " + e.getMessage());
        }
    }
}
