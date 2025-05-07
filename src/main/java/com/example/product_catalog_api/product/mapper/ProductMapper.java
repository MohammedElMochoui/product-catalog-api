package com.example.product_catalog_api.product.mapper;

import com.example.product_catalog_api.product.DTO.request.CreateProductRequestDTO;
import com.example.product_catalog_api.product.DTO.response.CreateProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.DeleteProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.GetProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.UpdateProductResponseDTO;
import com.example.product_catalog_api.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product createProductRequestDTOtoProduct(CreateProductRequestDTO createProductRequestDTO);

    @Mapping(source = "category.name", target = "category")
    CreateProductResponseDTO productToCreateProductResponseDTO(Product p);

    @Mapping(source = "category.name", target = "category")
    GetProductResponseDTO productToGetProductResponseDTO(Product p);

    UpdateProductResponseDTO productToUpdateProductResponseDTO(Product p);
    DeleteProductResponseDTO productToDeleteProductResponseDTO(Product p);
}
