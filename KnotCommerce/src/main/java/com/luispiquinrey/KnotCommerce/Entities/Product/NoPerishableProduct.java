package com.luispiquinrey.KnotCommerce.Entities.Product;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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

    public NoPerishableProduct(boolean available, Long id_Product, String name, double price,
                            String description, Integer stock, String warrantyPeriod) {
        super(available, id_Product, name, price, description, stock);
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
            this.warrantyPeriod
        );
    }
}
