package com.luispiquinrey.KnotCommerce.Entities.Product;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luispiquinrey.KnotCommerce.Entities.Category;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

@Inheritance(strategy=jakarta.persistence.InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="product_type",
        discriminatorType=jakarta.persistence.DiscriminatorType.STRING)
@Entity
@Table(name="Product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "description")
        })
public abstract class Product implements Serializable{


    @Column(name="available",columnDefinition="BIT")
    @JsonProperty("available")
    private boolean available;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_Product")
    @JsonProperty("id_Product")
    private Long id_Product;

    @Column(name="name")
    @JsonProperty("name")
    private String name;

    @Column(name="price")
    @JsonProperty("price")
    private double price;

    @Column(name="description")
    @JsonProperty("description")
    private String description;

    @Column(name="stock")
    @JsonProperty("stock")
    private Integer stock;

    @Version
    @Column(name="version")
    @JsonIgnore
    public Integer version;

    @ManyToMany
    @JoinTable(
        name = "Product_Category",
        joinColumns = @JoinColumn(name = "id_Product"),
        inverseJoinColumns = @JoinColumn(name = "id_Category")
    )
    private List<Category> categories;

    public Product() {}

    public Product(boolean available, Long id_Product, String name, double price, String description, Integer stock) {
        this.available = available;
        this.id_Product = id_Product;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }
    public Product(String name, double price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getId_Product() {
        return id_Product;
    }

    public void setId_Product(Long id_Product) {
        this.id_Product = id_Product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    public String productToJson() throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        String json=mapper.writeValueAsString(this);
        return json;
    }
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}