package com.example.product_catalog_api;

import com.example.product_catalog_api.product.exception.AllFieldsAreNullException;
import com.example.product_catalog_api.product.exception.CantFindCategoryException;
import com.example.product_catalog_api.product.exception.CantFindProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CantFindCategoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDetails handleCantFindCategoryException(CantFindCategoryException ex, WebRequest request) {
        ErrorDetails err = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return err;
    }

    @ExceptionHandler(CantFindProductException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDetails handleCantFindProductException(CantFindProductException ex, WebRequest request) {
        ErrorDetails err = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return err;
    }

    @ExceptionHandler(AllFieldsAreNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleAllFieldsAreNullException(AllFieldsAreNullException ex, WebRequest request) {
        ErrorDetails err = new ErrorDetails(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return err;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        log.warn("Validation failed for request [{}]: {}", request.getDescription(false), errors);
        return errors;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorDetails err = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return err;
    }

}

