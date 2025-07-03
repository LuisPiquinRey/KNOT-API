package com.luispiquinrey.KnotCommerce.Exceptions;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CategoryDeleteException extends RuntimeException{
    private Integer id_Category;
    public CategoryDeleteException(String message,Integer id_Category){
        super(message);
        this.id_Category=id_Category;
    }
    @Override
    public String getMessage() {
        String emoji = "⚠️";
        String timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now().atOffset(ZoneOffset.UTC));
        if (id_Category != null) {
            return String.format("CategoryDeleteException: %s [%s] %s (Product ID: %d)", emoji, timestamp, super.getMessage(), id_Category);
        } else {
            return String.format("CategoryDeleteException: %s [%s] %s", emoji, timestamp, super.getMessage());
        }
    }
}
