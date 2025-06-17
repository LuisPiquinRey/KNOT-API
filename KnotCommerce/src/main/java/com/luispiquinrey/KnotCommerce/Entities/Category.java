package com.luispiquinrey.KnotCommerce.Entities;

import java.io.Serializable;

import org.springframework.context.annotation.PropertySource;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "description")
        })
@PropertySource("KnotCommerce/src/main/resources/validationProduct.yml")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Category")
    @JsonProperty("id_Category")
    private Integer id_Category;

    @Column(name = "name")
    @JsonProperty("name")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "{category.length.name}")
    private String name;

    @Column(name = "description")
    @JsonProperty("description")
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 5, max = 100, message = "{category.length.description}")
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
                🗂️ Category Details {
                🔔 ID           : """ + id_Category +
                "\n   🏷️ Name         : \"" + name + "\"" +
                "\n   📝 Description  : \"" + description + "\"" +
                "\n}";
    }
}
