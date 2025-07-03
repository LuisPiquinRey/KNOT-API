package com.luispiquinrey.KnotCommerce.DTOs;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node(labels = "Category")
public class CategoryNode {

    @Id
    private Integer id_Category;
    private String name;

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.OUTGOING)
    private List<ProductNode> products;

    public CategoryNode() {}

    public CategoryNode(Integer id_Category, String name, List<ProductNode> products) {
        this.id_Category = id_Category;
        this.name = name;
        this.products = products;
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

    public List<ProductNode> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNode> products) {
        this.products = products;
    }
}
