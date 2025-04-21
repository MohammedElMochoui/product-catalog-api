package com.example.product_catalog_api;

import com.example.product_catalog_api.exception.AllFieldsAreNullException;
import com.example.product_catalog_api.exception.CantFindCategoryException;
import com.example.product_catalog_api.exception.CantFindProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CantFindCategoryException.class)
    ResponseEntity<ErrorDetails> handleCantFindCategoryException(CantFindCategoryException ex, WebRequest request) {
        ErrorDetails err = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(CantFindProductException.class)
    ResponseEntity<ErrorDetails> handleCantFindProductException(CantFindProductException ex, WebRequest request) {
        ErrorDetails err = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AllFieldsAreNullException.class)
    ResponseEntity<ErrorDetails> handleAllFieldsAreNullException(AllFieldsAreNullException ex, WebRequest request) {
        ErrorDetails err = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
