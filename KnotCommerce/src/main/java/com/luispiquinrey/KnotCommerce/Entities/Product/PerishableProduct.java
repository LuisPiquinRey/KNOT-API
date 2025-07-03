package com.luispiquinrey.KnotCommerce.Entities.Product;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luispiquinrey.KnotCommerce.Entities.Category;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    public PerishableProduct(boolean available, Long id_Product,
            @Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Length(min = 5, max = 100, message = "{product.length.description}") String description,
            @Min(value = 0, message = "{product.min.stock}") Integer stock, List<Category> categories,
            @NotNull(message = "{product.perishableProduct.notNull.expirationDate}") @Future(message = "{product.perishableProduct.future.expirationDate}") LocalDate expirationDate,
            @NotNull(message = "{product.perishableProduct.notNull.temperature}") Double recommendedTemperature) {
        super(available, name, price, description, stock,categories);
        this.expirationDate = expirationDate;
        this.recommendedTemperature = recommendedTemperature;
    }
    public PerishableProduct(@Length(min = 5, max = 20, message = "{product.length.name}") String name,
            @Positive(message = "{product.positive.price}") double price,
            @Min(value = 0, message = "{product.min.stock}") Integer stock,
            @NotNull(message = "{product.perishableProduct.notNull.expirationDate}") @Future(message = "{product.perishableProduct.future.expirationDate}") LocalDate expirationDate,
            @NotNull(message = "{product.perishableProduct.notNull.temperature}") Double recommendedTemperature) {
        super(name, price, stock);
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
            this.getCategories(),
            this.expirationDate,
            this.recommendedTemperature
        );
    }
}
