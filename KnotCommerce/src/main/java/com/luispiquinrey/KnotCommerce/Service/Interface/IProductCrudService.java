package com.luispiquinrey.KnotCommerce.Service.Interface;

import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductCreationException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductDeleteException;
import com.luispiquinrey.KnotCommerce.Exceptions.ProductUpdateException;

import jakarta.persistence.EntityNotFoundException;

public interface IProductCrudService {
    void deleteProductById(Long id_Product) throws ProductDeleteException;
    void updateProduct (Product product) throws ProductUpdateException;
    void createProduct(Product product) throws ProductCreationException;
}
