package com.luispiquinrey.KnotCommerce.Service.Facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Service.Implementation.ImplServiceCategory;
import com.luispiquinrey.KnotCommerce.Service.Implementation.ImplServiceCategoryNode;
import com.luispiquinrey.KnotCommerce.Service.Interface.ICrudService;

@Service
public class FacadeServiceCategory implements ICrudService<Category, Integer> {

    @Autowired
    private final ImplServiceCategory implServiceCategory;

    @Autowired
    private final ImplServiceCategoryNode implServiceCategoryNode;

    public FacadeServiceCategory(ImplServiceCategory implServiceCategory,
            ImplServiceCategoryNode implServiceCategoryNode) {
        this.implServiceCategory = implServiceCategory;
        this.implServiceCategoryNode = implServiceCategoryNode;
    }

    @Override
    public void createTarget(Category category) {
        implServiceCategory.createTarget(category);
        implServiceCategoryNode.createTarget(category);
    }

    @Override
    public void updateTarget(Category category) {
        implServiceCategory.updateTarget(category);
        implServiceCategoryNode.updateTarget(category);
    }

    @Override
    public void deleteTargetById(Integer id) {
        implServiceCategory.deleteTargetById(id);
        implServiceCategoryNode.deleteTargetById(id);
    }

    public Category getCategoryOrThrow(Integer id_Category) {
        return implServiceCategory.getCategoryOrThrow(id_Category);
    }

    public List<Category> findAllCategories() {
        return implServiceCategory.findAllCategories();
    }
}
