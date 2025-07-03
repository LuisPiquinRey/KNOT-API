package com.luispiquinrey.KnotCommerce.Service.Implementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Exceptions.CategoryCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.CategoryDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.CategoryUpdateException;
import com.luispiquinrey.KnotCommerce.Repository.RepositoryCategory;
import com.luispiquinrey.KnotCommerce.Service.Interface.IServiceCategory;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ImplServiceCategory implements IServiceCategory {

    private static final Logger logger = LoggerFactory.getLogger(ImplServiceCategory.class);

    @Autowired
    private final RepositoryCategory repositoryCategory;

    public ImplServiceCategory(RepositoryCategory repositoryCategory) {
        this.repositoryCategory = repositoryCategory;
    }

    @Transactional
    @Override
    public void createTarget(Category category) throws CategoryCreationException {
        try {
            repositoryCategory.save(category);
            logger.info("\u001B[32m🎉 [CATEGORY CREATED] ➤ Category created successfully:\n{}\u001B[0m", category);
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [CREATE CATEGORY FAILED] ➤ Error creating category '{}': {}\u001B[0m",
                    category.getName(), e.getMessage(), e);
            throw new CategoryCreationException("Error creating category: " + e.getMessage(),
                    category.getId_Category());
        }
    }

    @Transactional
    @Override
    public void deleteTargetById(Integer id_Category) throws CategoryDeleteException {
        if (repositoryCategory.existsById(id_Category)) {
            try {
                repositoryCategory.deleteById(id_Category);
                logger.info("\u001B[31m🗑️ [CATEGORY DELETED] ➤ Category with ID {} was deleted successfully.\u001B[0m",
                        id_Category);
            } catch (Exception e) {
                logger.error("\u001B[31m🚨 [DELETE CATEGORY FAILED] ➤ Error deleting category with ID {}: {}\u001B[0m",
                        id_Category, e.getMessage(), e);
                throw new CategoryDeleteException("Error deleting category: " + e.getMessage(), id_Category);
            }
        } else {
            logger.warn("\u001B[33m❌ [DELETE CATEGORY FAILED] ➤ Category with ID {} does not exist.\u001B[0m",
                    id_Category);
            throw new CategoryDeleteException("Category with ID " + id_Category + " does not exist.", id_Category);
        }
    }

    @Transactional
    @Override
    public void updateTarget(Category category) throws CategoryUpdateException {
        Integer id = category.getId_Category();
        Category managed = repositoryCategory.findById(id)
                .orElseThrow(() -> new CategoryUpdateException("Category with ID " + id + " does not exist.", id));

        managed.setName(category.getName());
        managed.setDescription(category.getDescription());

        try {
            repositoryCategory.save(managed);
            logger.info("\u001B[36m🛠️ [CATEGORY UPDATED] ➤ Category with ID {} updated successfully.\u001B[0m", id);
        } catch (Exception e) {
            logger.error("\u001B[31m🚨 [UPDATE CATEGORY FAILED] ➤ Error updating category with ID {}: {}\u001B[0m", id,
                    e.getMessage(), e);
            throw new CategoryUpdateException("Error updating category: " + e.getMessage(), id);
        }
    }

    @Override
    public Category getCategoryOrThrow(Integer id_Category) throws EntityNotFoundException {
        return repositoryCategory.findById(id_Category)
                .map(category -> {
                    logger.info("\u001B[32m🔍 [CATEGORY FOUND] ➤ Category with ID {} retrieved successfully.\u001B[0m",
                            id_Category);
                    return category;
                })
                .orElseThrow(() -> {
                    logger.warn("\u001B[33m❌ [NOT FOUND] ➤ Category with ID {} was not found in the database.\u001B[0m",
                            id_Category);
                    return new EntityNotFoundException("Category with ID " + id_Category + " not found.");
                });
    }

    @Override
    public List<Category> findAllCategories() {
        return repositoryCategory.findAll();
    }

}
