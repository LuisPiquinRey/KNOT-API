package com.luispiquinrey.KnotCommerce.Exceptions;

public class ProductNodeUpdateException extends ProductUpdateException{

    public ProductNodeUpdateException(String message, Long id_Product) {
        super(message, id_Product);
    }

    public ProductNodeUpdateException(String message) {
        super(message, null);
    }
}
