package com.luispiquinrey.KnotCommerce.DTOs;

import com.luispiquinrey.KnotCommerce.Enums.Tactic;

public class ProductPaymentDTO {
    private Tactic tactic;
    private Long id_Product;
    private String name;
    private double price;
    private String description;
    public ProductPaymentDTO(Tactic tactic,Long id_Product, String name, double price, String description) {
        this.tactic=tactic;
        this.id_Product = id_Product;
        this.name = name;
        this.price = price;
        this.description=description;
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
    public Tactic getTactic() {
        return tactic;
    }
    public void setTactic(Tactic tactic) {
        this.tactic = tactic;
    }
}
