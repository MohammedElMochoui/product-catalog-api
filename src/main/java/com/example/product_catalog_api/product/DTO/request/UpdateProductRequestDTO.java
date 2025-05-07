package com.example.product_catalog_api.product.DTO.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductRequestDTO(
        @PositiveOrZero(message = "Price must be 0 or higher!")
        @Digits(integer = 5, fraction = 2, message = "Only prices up to 99999.99 are accepted!")
        BigDecimal price,
        @NotBlank(message = "Description cannot be empty or null!")
        String description
) {
}
