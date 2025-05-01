package com.luispiquinrey.apiknot.Entities.Product.ProductPackage.FactoryProduct;


import com.luispiquinrey.apiknot.Entities.Product.ProductPackage.NoPerishableProduct;
import com.luispiquinrey.apiknot.Entities.Product.ProductPackage.Product;

public class CreatorNoPerishableProduct extends CreatorFactory{
    @Override
    public Product create() {
        return new NoPerishableProduct.NoPerishableProductBuilder().build();
    }
}
