package com.example.product_catalog_api.DTO.response;

import java.util.List;

public record GetProductsResponseDTO(
        int count,
        List<GetProductResponseDTO> products
){
}
