package com.example.product_catalog_api.product.DTO.response;

import com.example.product_catalog_api.product.model.CategoryEnum;

import java.math.BigDecimal;

public record CreateProductResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        CategoryEnum category
) {
}
