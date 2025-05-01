package com.luispiquinrey.apiknot.Entities.Product.ProductPackage;

import com.luispiquinrey.apiknot.Entities.Builder;
import com.luispiquinrey.apiknot.Entities.Category;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("NO_PERISHABLE")
public class NoPerishableProduct extends Product {

    public NoPerishableProduct(NoPerishableProductBuilder noPerishableProductBuilder){
        this.setProduct_id(noPerishableProductBuilder.product_id);
        this.setBrand(noPerishableProductBuilder.brand);
        this.setStock(noPerishableProductBuilder.stock);
        this.setPrice(noPerishableProductBuilder.price);
        this.setCategories(noPerishableProductBuilder.categories);
    }
    public NoPerishableProduct() {
        super();
    }
    @Override
    public void discount() {

    }
    public static class NoPerishableProductBuilder implements Builder<NoPerishableProduct> {
        private Integer product_id;
        private String brand;
        private Integer stock;
        private float price;
        private List<Category> categories;
        public NoPerishableProductBuilder product_id(Integer product_id) {
            this.product_id = product_id;
            return this;
        }
        public NoPerishableProductBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }
        public NoPerishableProductBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }
        public NoPerishableProductBuilder price(float price) {
            this.price = price;
            return this;
        }
        public NoPerishableProductBuilder categories(List<Category> categories) {
            this.categories = categories;
            return this;
        }
        @Override
        public NoPerishableProduct build() {
            return new NoPerishableProduct(this);
        }
    }
}
