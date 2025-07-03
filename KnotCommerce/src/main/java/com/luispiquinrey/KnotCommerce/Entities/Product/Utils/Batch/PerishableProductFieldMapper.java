package com.luispiquinrey.KnotCommerce.Entities.Product.Utils.Batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.boot.context.properties.bind.BindException;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.PerishableProduct;

public class PerishableProductFieldMapper implements FieldSetMapper<PerishableProduct> {
    @Override
    public PerishableProduct mapFieldSet(FieldSet fieldSet) throws BindException {
        PerishableProduct product = new PerishableProduct();
        product.setAvailable(fieldSet.readBoolean("available"));
        product.setName(fieldSet.readString("name"));
        product.setPrice(fieldSet.readDouble("price"));
        product.setDescription(fieldSet.readString("description"));
        product.setStock(fieldSet.readInt("stock"));
        String categoriesStr = fieldSet.readString("categories");
        List<Category> categories = new ArrayList<>();
        if (categoriesStr != null && !categoriesStr.isBlank()) {
            String[] names = categoriesStr.contains(";") ? categoriesStr.split(";") : categoriesStr.split(",");
            for (String name : names) {
                Category c = new Category();
                c.setName(name.trim());
                categories.add(c);
            }
        }
        product.setCategories(categories);
        product.setVersion(fieldSet.readInt("version"));
        product.setExpirationDate(java.time.LocalDate.parse(fieldSet.readString("expirationDate")));
        product.setRecommendedTemperature(fieldSet.readDouble("recommendedTemperature"));
        return product;
    }
}
