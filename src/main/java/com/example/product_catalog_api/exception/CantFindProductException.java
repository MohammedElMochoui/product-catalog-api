package com.example.product_catalog_api.exception;


import com.example.product_catalog_api.model.Category;
import com.example.product_catalog_api.model.CategoryEnum;
import com.example.product_catalog_api.model.Product;

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
