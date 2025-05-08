package com.example.product_catalog_api.dto;

public record ErrorDetails(
        int statucode,
        String message,
        String details
) {
}
