package com.luispiquinrey.KnotCommerce.Service.Interface;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Exceptions.CategoryCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.CategoryDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.CategoryUpdateException;

import jakarta.persistence.EntityNotFoundException;

public interface IServiceCategory {
    void createCategory(Category category) throws CategoryCreationException;
    void deleteCategory(Integer id_Category) throws CategoryDeleteException;
    void updateCategory(Category category) throws CategoryUpdateException;
    public Category getCategoryOrThrow(Integer id_Category) throws EntityNotFoundException;
}
