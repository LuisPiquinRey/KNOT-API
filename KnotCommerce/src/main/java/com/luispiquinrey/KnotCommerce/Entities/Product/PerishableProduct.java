package com.luispiquinrey.KnotCommerce.Entities.Product;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PerishableProduct")
public class PerishableProduct extends Product implements PrototypeProduct {

    @Column(name = "expiration_date")
    @JsonProperty("expiration_date")
    private LocalDate expirationDate;

    @Column(name = "recommended_temperature")
    @JsonProperty("recommended_temperature")
    private Double recommendedTemperature;

    public PerishableProduct() {
        super();
    }

    public PerishableProduct(boolean available, Integer id_Product, String name, double price, String description,
                            Integer stock, LocalDate expirationDate, Double recommendedTemperature) {
        super(available, id_Product, name, price, description, stock);
        this.expirationDate = expirationDate;
        this.recommendedTemperature = recommendedTemperature;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getRecommendedTemperature() {
        return recommendedTemperature;
    }

    public void setRecommendedTemperature(Double recommendedTemperature) {
        this.recommendedTemperature = recommendedTemperature;
    }

    @Override
    public String toString() {
        return """
                🥫 Perishable Product {
                🆔 ID                    : """ + getId_Product() +
            "\n   🏷️ Name                  : \"" + getName() + "\"" +
            "\n   💰 Price                 : $" + getPrice() +
            "\n   📝 Description           : \"" + getDescription() + "\"" +
            "\n   📦 Stock                 : " + getStock() +
            "\n   ✅ Available             : " + (isAvailable() ? "Yes" : "No") +
            "\n   📅 Expiration Date       : " + (expirationDate != null ? expirationDate : "N/A") +
            "\n   ❄️ Recommended Temp.     : " + (recommendedTemperature != null ? recommendedTemperature + "°C" : "N/A") +
            "\n}";
    }

    @Override
    public Product clone() {
        return new PerishableProduct(
                this.isAvailable(),
                this.getId_Product(),
                this.getName(),
                this.getPrice(),
                this.getDescription(),
                this.getStock(),
                this.expirationDate,
                this.recommendedTemperature
        );
    }
}
