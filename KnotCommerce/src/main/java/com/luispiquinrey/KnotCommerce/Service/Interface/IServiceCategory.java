package com.luispiquinrey.KnotCommerce.Service.Interface;

import java.util.List;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.common_tools.Service.ICrudService;

import jakarta.persistence.EntityNotFoundException;

public interface IServiceCategory extends ICrudService<Category, Integer>{
    public Category getCategoryOrThrow(Integer id_Category) throws EntityNotFoundException;
    List<Category> findAllCategories();
}
