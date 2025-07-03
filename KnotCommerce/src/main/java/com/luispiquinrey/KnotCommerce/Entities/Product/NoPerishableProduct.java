package com.luispiquinrey.KnotCommerce.Entities.Product;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luispiquinrey.KnotCommerce.Entities.Category;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Entity
@DiscriminatorValue("NoPerishableProduct")
@PropertySource("KnotCommerce/src/main/resources/validationProduct.yml")
public class NoPerishableProduct extends Product implements PrototypeProduct {

    @Column(name = "warranty_period")
    @JsonProperty("warranty_period")
    @Length(min=5, max=20, message = "{product.noPerishableProduct.length.warrantyPeriod}")
    private String warrantyPeriod;

    public NoPerishableProduct() {
        super();
    }

    public NoPerishableProduct(boolean available, Long id_Product,
            @Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Length(min = 5, max = 100, message = "{product.length.description}") String description,
            @Min(value = 0, message = "{product.min.stock}") Integer stock, List<Category> categories,
            @Length(min = 5, max = 20, message = "{product.noPerishableProduct.length.warrantyPeriod}") String warrantyPeriod) {
        super(available,name, price, description, stock, categories);
        this.warrantyPeriod = warrantyPeriod;
    }

    public NoPerishableProduct(String name, double price, Integer stock,
            @Length(min = 5, max = 20, message = "{product.noPerishableProduct.length.warrantyPeriod}") String warrantyPeriod) {
        super(name, price, stock);
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return """
                \ud83d\udce6 No-Perishable Product {
                \ud83c\udd94 ID                  : """ + getId_Product() +
            "\n   🏷️ Name                : \"" + getName() + "\"" +
            "\n   💰 Price               : $" + getPrice() +
            "\n   📝 Description         : \"" + getDescription() + "\"" +
            "\n   📦 Stock               : " + getStock() +
            "\n   ✅ Available           : " + (isAvailable() ? "Yes" : "No") +
            "\n   🛡️ Warranty Period     : " + (warrantyPeriod != null ? warrantyPeriod : "N/A") +
            "\n}";
    }

    @Override
    public Product clone() {
        return new NoPerishableProduct(
            this.isAvailable(),
            this.getId_Product(),
            this.getName(),
            this.getPrice(),
            this.getDescription(),
            this.getStock(),
            this.getCategories(),
            this.warrantyPeriod
        );
    }
}
