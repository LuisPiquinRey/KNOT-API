package com.luispiquinrey.KnotCommerce.Service;

import java.util.List;

import com.luispiquinrey.KnotCommerce.DTOs.ProductNode;

import jakarta.persistence.EntityNotFoundException;

public interface IServiceProductNode {
    ProductNode save(ProductNode productNode);
    List<ProductNode> findAll();
    ProductNode getProductNodeOrThrow(Long id_Product) throws EntityNotFoundException;
    void deleteById(Long id);
}