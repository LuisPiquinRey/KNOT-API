package com.luispiquinrey.apiknot.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


@Entity
@Table(name = "Categories")
public class Category {
    @Lob
    @JsonProperty("description")
    @jakarta.persistence.Column(columnDefinition = "LONGTEXT")
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("category_id")
    @jakarta.persistence.Column(name = "category_id")
    private Integer category_id;

    public Category(String description, Integer category_id) {
        this.description = description;
        this.category_id = category_id;
    }
    public Category() {}
    @Override
    public String toString() {
        return "\n🏷️ Category Info:" +
           "\n🆔 ID           : " + category_id +
           "\n📝 Description  : " + description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
}
