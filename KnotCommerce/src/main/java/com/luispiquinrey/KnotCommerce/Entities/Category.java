package com.luispiquinrey.KnotCommerce.Entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "description")
        })
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Category")
    @JsonProperty("id_Category")
    private Integer id_Category;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    public Category() {}

    public Category(Integer id_Category, String name, String description) {
        this.id_Category = id_Category;
        this.name = name;
        this.description = description;
    }

    public Integer getId_Category() {
        return id_Category;
    }

    public void setId_Category(Integer id_Category) {
        this.id_Category = id_Category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return """
                \ud83d\uddc2\ufe0f Category Details {
                \ud83c\udd94 ID           : """ + id_Category +
            "\n   🏷️ Name         : \"" + name + "\"" +
            "\n   📝 Description  : \"" + description + "\"" +
            "\n}";
    }
}
