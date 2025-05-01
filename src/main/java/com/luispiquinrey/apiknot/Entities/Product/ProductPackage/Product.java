package com.luispiquinrey.apiknot.Entities.Product.ProductPackage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.luispiquinrey.apiknot.Entities.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "Products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
public abstract class Product {
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
    public abstract void discount();

    public Product(Integer product_id, String brand, @Min(0) @Max(1000) Integer stock,
            @Positive float price, List<Category> categories) {
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
}
