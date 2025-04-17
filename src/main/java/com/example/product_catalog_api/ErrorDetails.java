package com.example.product_catalog_api;

public record ErrorDetails(
        int statucode,
        String message,
        String details
) {
}
