package com.luispiquinrey.KnotCommerce.Entities.Product.Utils.Batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.NoPerishableProduct;

public class NoPerishableProductFieldMapper implements FieldSetMapper<NoPerishableProduct> {

    @Override
    public NoPerishableProduct mapFieldSet(FieldSet fieldSet) throws BindException {
        NoPerishableProduct product = new NoPerishableProduct();

        product.setAvailable(fieldSet.readBoolean("available"));
        product.setName(fieldSet.readString("name"));
        product.setPrice(fieldSet.readBigDecimal("price").doubleValue());
        product.setDescription(fieldSet.readString("description"));
        product.setStock(fieldSet.readInt("stock"));
        product.setVersion(fieldSet.readInt("version"));

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

        product.setWarrantyPeriod(fieldSet.readString("warrantyPeriod"));

        return product;
    }
}