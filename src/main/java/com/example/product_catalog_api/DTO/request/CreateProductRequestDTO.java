package com.example.product_catalog_api.DTO.request;

import com.example.product_catalog_api.model.CategoryEnum;

import java.math.BigDecimal;

public record CreateProductRequestDTO(
        String name,
        BigDecimal price,
        String description,
        CategoryEnum category
) {
}
