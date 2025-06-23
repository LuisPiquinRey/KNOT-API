package com.luispiquinrey.KnotCommerce.Exceptions;

public class ProductNodeDeleteException extends ProductDeleteException {

    public ProductNodeDeleteException(String message, Long id_Product) {
        super(message, id_Product);
    }
    public ProductNodeDeleteException(String message) {
        super(message, null);
    }
}
