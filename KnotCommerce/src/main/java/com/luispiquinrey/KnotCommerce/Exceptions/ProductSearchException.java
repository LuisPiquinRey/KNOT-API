package com.luispiquinrey.KnotCommerce.Exceptions;

public class ProductSearchException extends RuntimeException{
    ProductSearchException(String message){
        super(message);
    }
}