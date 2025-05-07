package com.example.product_catalog_api.user.dto.request;

public record LoginRequestDTO(
        String username,
        String password
) {
}
