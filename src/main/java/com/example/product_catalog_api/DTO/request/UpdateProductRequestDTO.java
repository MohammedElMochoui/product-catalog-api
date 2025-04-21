package com.example.product_catalog_api.DTO.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public record UpdateProductRequestDTO(
        BigDecimal price,
        String description
) {
}
