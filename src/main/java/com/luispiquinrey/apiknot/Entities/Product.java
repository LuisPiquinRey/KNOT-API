package com.luispiquinrey.apiknot.Entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "Products")
public class Product {
    @JsonProperty("expiration_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expiration_date")
    private LocalDate expiration_date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("product_id")
    @Column(name = "product_id")
    private Integer product_id;

    @JsonProperty("brand")
    @Column(length = 50)
    private String brand;

    @JsonProperty("stock")
    @Min(0) @Max(1000)
    @Column(name = "stock")
    private Integer stock;

    @JsonProperty("price")
    @Positive
    @Column(name = "price")
    private float price;

    @JsonProperty("qrCode")
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] qrCode;

    @JsonProperty("categories")
    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @jakarta.persistence.JoinColumn(name = "product_id"),
        inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "category_id")
    )
    private List<Category> categories;


    public Product(LocalDate expiration_date, Integer product_id, String brand, @Min(0) @Max(1000) Integer stock,
            @Positive float price, List<Category> categories) {
        this.expiration_date = expiration_date;
        this.product_id = product_id;
        this.brand = brand;
        this.stock = stock;
        this.price = price;
        this.categories = categories;
    }
    public Product() {
    }

    @Override
    public String toString() {
        return "\n🛍️ Product Details:" +
           "\n🆔 ID            : " + product_id +
           "\n🏷️ Brand         : " + brand +
           "\n📦 Stock         : " + stock +
           "\n💰 Price         : $" + price +
           "\n📅 Expiration    : " + expiration_date +
           "\n🏷️ Categories    : " + (categories != null ? categories.size() + " category(ies)" : "none");
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public byte[] getQrCode() {
        return qrCode;
    }
    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }
    public LocalDate getExpiration_date() {
        return expiration_date;
    }
    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public static User fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, User.class);
    }
}
