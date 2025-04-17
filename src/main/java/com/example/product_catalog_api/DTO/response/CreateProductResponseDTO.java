package com.example.product_catalog_api.DTO.response;

import com.example.product_catalog_api.model.CategoryEnum;

import java.math.BigDecimal;

public record CreateProductResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        CategoryEnum category
) {
}
