package com.example.product_catalog_api.product.exception;


public class CantFindProductException extends RuntimeException{
    public CantFindProductException(String message) {
        super(message);
    }

    public static CantFindProductException create(Long id) {
        String message = "Cannot find product with id: " + id;
        return new CantFindProductException(message);
    }

    public static CantFindProductException create(String name) {
        String message = "Cannot find product with name: " + name;
        return new CantFindProductException(message);
    }
}
