package com.luispiquinrey.apiknot.Entities.Product.ProductPackage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luispiquinrey.apiknot.Entities.Builder;
import com.luispiquinrey.apiknot.Entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("PERISHABLE")
public class PerishableProduct extends Product{
    @JsonProperty("expirationDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "storage_temperature")
    private double storageTemperature;

    private PerishableProduct(PerishableProductBuilder perishableProductBuilder) {
        this.expirationDate = perishableProductBuilder.expirationDate;
        this.storageTemperature=perishableProductBuilder.storageTemperature;
        this.setProduct_id(perishableProductBuilder.product_id);
        this.setBrand(perishableProductBuilder.brand);
        this.setStock(perishableProductBuilder.stock);
        this.setPrice(perishableProductBuilder.price);
        this.setCategories(perishableProductBuilder.categories);
    }
    public PerishableProduct() {
        super();
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getStorageTemperature() {
        return storageTemperature;
    }

    public void setStorageTemperature(double storageTemperature) {
        this.storageTemperature = storageTemperature;
    }

    @Override
    public void discount() {
    }
    public static class PerishableProductBuilder implements Builder<PerishableProduct> {
        private Integer product_id;
        private String brand;
        private Integer stock;
        private float price;
        private List<Category> categories;
        private LocalDate expirationDate;
        private double storageTemperature;
        public PerishableProductBuilder product_id(Integer product_id) {
            this.product_id = product_id;
            return this;
        }
        public PerishableProductBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }
        public PerishableProductBuilder stock(Integer stock) {
            this.stock = stock;
            return this;
        }
        public PerishableProductBuilder price(float price) {
            this.price = price;
            return this;
        }
        public PerishableProductBuilder categories(List<Category> categories) {
            this.categories = categories;
            return this;
        }
        public PerishableProductBuilder expirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }
        public PerishableProductBuilder storageTemperature(double storageTemperature) {
            this.storageTemperature = storageTemperature;
            return this;
        }
        @Override
        public PerishableProduct build() {
            return new PerishableProduct(this);
        }
    }
}
