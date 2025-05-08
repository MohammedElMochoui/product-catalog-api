package com.example.product_catalog_api.product.service;


import com.example.product_catalog_api.product.DTO.response.CreateProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.DeleteProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.GetProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.UpdateProductResponseDTO;
import com.example.product_catalog_api.product.exception.CantFindCategoryException;
import com.example.product_catalog_api.product.exception.CantFindProductException;
import com.example.product_catalog_api.product.mapper.ProductMapper;
import com.example.product_catalog_api.product.entity.Category;
import com.example.product_catalog_api.product.entity.CategoryEnum;
import com.example.product_catalog_api.product.entity.Product;
import com.example.product_catalog_api.product.repository.CategoryRepository;
import com.example.product_catalog_api.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public Page<GetProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        List<GetProductResponseDTO> responses = products.getContent().stream().map(productMapper::productToGetProductResponseDTO).toList();
        return new PageImpl<>(responses, products.getPageable(), products.getTotalElements());
    }

    @Transactional(readOnly = true)
    public GetProductResponseDTO getProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> CantFindProductException.create(id));
        return productMapper.productToGetProductResponseDTO(p);
    }

    @Transactional(readOnly = true)
    public GetProductResponseDTO getProduct(String name) {
        Product p = productRepository.findByName(name)
                .orElseThrow(() -> CantFindProductException.create(name));
        return productMapper.productToGetProductResponseDTO(p);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CreateProductResponseDTO createProduct(String name, BigDecimal price, CategoryEnum category, String description) {
        Category c = categoryRepository.findByName(category)
                .orElseThrow(() -> CantFindCategoryException.create(category));
        Product p = new Product(name, price, c, description);
        Product savedProduct = productRepository.save(p);
        return productMapper.productToCreateProductResponseDTO(savedProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UpdateProductResponseDTO updateProduct(Long id, BigDecimal price, String description) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> CantFindProductException.create(id));

        if (price != null)
            p.setPrice(price);

        if (description != null)
            p.setDescription(description);

        return productMapper.productToUpdateProductResponseDTO(p);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public DeleteProductResponseDTO deleteProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> CantFindProductException.create(id));

        productRepository.delete(p);
        return productMapper.productToDeleteProductResponseDTO(p);
    }
}
