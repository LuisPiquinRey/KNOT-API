package com.luispiquinrey.KnotCommerce.Exceptions;

public class ProductDeleteException extends RuntimeException{
    public ProductDeleteException(String message){
        super(message);
    }
}
