package com.luispiquinrey.KnotCommerce.DTOs;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

@Node(labels="Product")
public class ProductNode {

    @Id
    private Long id_Product;

    @Relationship(type="DIRECTED", direction=Direction.INCOMING)
    private List<CategoryNode> categories;
    public ProductNode() {
    }

    public ProductNode(Long id_Product, List<CategoryNode> categories) {
        this.id_Product = id_Product;
        this.categories = categories;
    }

    public Long getId_Product() {
        return id_Product;
    }

    public void setId_Product(Long id_Product) {
        this.id_Product = id_Product;
    }

    public List<CategoryNode> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryNode> categories) {
        this.categories = categories;
    }
}
