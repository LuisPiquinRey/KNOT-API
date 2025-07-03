package com.luispiquinrey.KnotCommerce.Exceptions;

public class ProductNodeCreationException extends ProductCreationException {

    public ProductNodeCreationException(String message, Long id_Product) {
        super(message, id_Product);
    }

    public ProductNodeCreationException(String message) {
        super(message, null);
    }
}