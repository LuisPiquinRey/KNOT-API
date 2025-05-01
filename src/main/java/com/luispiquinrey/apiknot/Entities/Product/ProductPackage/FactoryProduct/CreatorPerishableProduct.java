package com.luispiquinrey.apiknot.Entities.Product.ProductPackage.FactoryProduct;

import com.luispiquinrey.apiknot.Entities.Product.ProductPackage.PerishableProduct;
import com.luispiquinrey.apiknot.Entities.Product.ProductPackage.Product;

public class CreatorPerishableProduct extends CreatorFactory{
    @Override
    public Product create() {
        return new PerishableProduct.PerishableProductBuilder().build();
    }
}
