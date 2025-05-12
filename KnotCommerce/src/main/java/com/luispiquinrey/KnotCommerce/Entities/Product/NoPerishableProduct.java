package com.luispiquinrey.KnotCommerce.Entities.Product;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NoPerishableProduct")
public class NoPerishableProduct extends Product {

    @Column(name = "warranty_period")
    @JsonProperty("warranty_period")
    private String warrantyPeriod;

    public NoPerishableProduct() {
        super();
    }

    public NoPerishableProduct(boolean available, Integer id_Product, String name, double price,
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
}
