package com.luispiquinrey.KnotCommerce.Exceptions;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ProductUpdateException extends RuntimeException{
    private Long id_Product;
    public ProductUpdateException(String message,Long id_Product){
        super(message);
        this.id_Product=id_Product;
    }
    @Override
    public String getMessage() {
        String emoji = "⚠️";
        String timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now().atOffset(ZoneOffset.UTC));
        if (id_Product != null) {
            return String.format("ProductUpdateException: %s [%s] %s (Product ID: %d)", emoji, timestamp, super.getMessage(), id_Product);
        } else {
            return String.format("ProductUpdateException: %s [%s] %s", emoji, timestamp, super.getMessage());
        }
    }
}
