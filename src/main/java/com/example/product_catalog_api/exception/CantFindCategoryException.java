package com.example.product_catalog_api.exception;


import com.example.product_catalog_api.model.Category;
import com.example.product_catalog_api.model.CategoryEnum;

public class CantFindCategoryException extends RuntimeException{
    public CantFindCategoryException(String message) {
        super(message);
    }

    public static CantFindCategoryException create(CategoryEnum c) {
        String message = "Cannot find category with name: " + c.name();
        return new CantFindCategoryException(message);
    }

    public static CantFindCategoryException create(Category c) {
        String message = "Cannot find category with name: " + c.getName();
        return new CantFindCategoryException(message);
    }
}
