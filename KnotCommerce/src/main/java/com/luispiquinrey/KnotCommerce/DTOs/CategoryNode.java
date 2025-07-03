package com.luispiquinrey.KnotCommerce.DTOs;

import org.springframework.data.neo4j.core.schema.Node;

@Node(labels="Category")
public class CategoryNode {
    private Integer id_Category;

    private String name;

    public CategoryNode() {
    }

    public CategoryNode(Integer id_Category,String name) {
        this.id_Category = id_Category;
        this.name=name;
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
    
}
