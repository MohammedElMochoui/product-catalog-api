package com.example.product_catalog_api.product.DTO.response;

import com.example.product_catalog_api.product.entity.CategoryEnum;

import java.math.BigDecimal;

public record GetProductResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        CategoryEnum category
) {
}
