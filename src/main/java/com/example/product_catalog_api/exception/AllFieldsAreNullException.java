package com.example.product_catalog_api.exception;

public class AllFieldsAreNullException extends RuntimeException {
    public AllFieldsAreNullException(String message) {
        super(message);
    }
}
