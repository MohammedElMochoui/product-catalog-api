package com.example.product_catalog_api.product.DTO.request;

import com.example.product_catalog_api.product.entity.CategoryEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateProductRequestDTO(
        @NotBlank(message = "Name cannot be empty or null!")
        String name,
        @PositiveOrZero(message = "Price must be 0 or higher!")
        @Digits(integer = 5, fraction = 2, message = "Only prices up to 99999.99 are accepted!")
        BigDecimal price,
        @NotBlank(message = "Description cannot be empty or null!")
        String description,
        @NotNull(message = "Category is mandatory!")
        CategoryEnum category
) {
}
