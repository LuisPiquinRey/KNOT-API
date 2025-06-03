package com.luispiquinrey.KnotCommerce.Entities.Product.Utils.Batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.luispiquinrey.KnotCommerce.Entities.Product.NoPerishableProduct;
import com.luispiquinrey.KnotCommerce.Entities.Product.PerishableProduct;

public class NoPerishableProductFieldMapper implements FieldSetMapper<NoPerishableProduct> {
    @Override
    public NoPerishableProduct mapFieldSet(FieldSet fieldSet) throws BindException {
        NoPerishableProduct product = new NoPerishableProduct();
        product.setAvailable(fieldSet.readBoolean("available"));
        product.setId_Product(fieldSet.readLong("id_Product"));
        product.setName(fieldSet.readString("name"));
        product.setPrice(fieldSet.readDouble("price"));
        product.setDescription(fieldSet.readString("description"));
        product.setStock(fieldSet.readInt("stock"));
        product.setVersion(fieldSet.readInt("version"));
        String categoriesStr = fieldSet.readString("categories");
        if (categoriesStr != null && !categoriesStr.isBlank()) {
            List<com.luispiquinrey.KnotCommerce.Entities.Category> categories = new ArrayList<>();
            for (String name : categoriesStr.split(",")) {
                com.luispiquinrey.KnotCommerce.Entities.Category cat = new com.luispiquinrey.KnotCommerce.Entities.Category();
                cat.setName(name.trim());
                categories.add(cat);
            }
            product.setCategories(categories);
        }
        product.setWarrantyPeriod(fieldSet.readString("warrantyPeriod"));
        return product;
    }
}
