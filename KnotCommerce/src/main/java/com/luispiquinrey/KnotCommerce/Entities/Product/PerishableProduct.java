package com.luispiquinrey.KnotCommerce.Entities.Product;

import java.time.LocalDate;

import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("PerishableProduct")
@PropertySource("KnotCommerce/src/main/resources/validationProduct.yml")
public class PerishableProduct extends Product implements PrototypeProduct {

    @NotNull(message = "{product.perishableProduct.notNull.expirationDate}")
    @Future(message = "{product.perishableProduct.future.expirationDate}")
    @Column(name = "expiration_date")
    @JsonProperty("expiration_date")
    private LocalDate expirationDate;

    @NotNull(message = "{product.perishableProduct.notNull.temperature}")
    @Column(name = "recommended_temperature",precision=2)
    @JsonProperty("recommended_temperature")
    private Double recommendedTemperature;

    public PerishableProduct() {
        super();
    }

    public PerishableProduct(boolean available, Long id_Product, String name, double price, String description,
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
