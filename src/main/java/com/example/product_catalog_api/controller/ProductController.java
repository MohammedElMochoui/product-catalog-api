package com.example.product_catalog_api.controller;

import com.example.product_catalog_api.DTO.request.CreateProductRequestDTO;
import com.example.product_catalog_api.DTO.request.UpdateProductRequestDTO;
import com.example.product_catalog_api.DTO.response.CreateProductResponseDTO;
import com.example.product_catalog_api.DTO.response.DeleteProductResponseDTO;
import com.example.product_catalog_api.DTO.response.GetProductResponseDTO;
import com.example.product_catalog_api.DTO.response.UpdateProductResponseDTO;
import com.example.product_catalog_api.exception.AllFieldsAreNullException;
import com.example.product_catalog_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<GetProductResponseDTO>> getAllProducts(Pageable pageable) {
        Page<GetProductResponseDTO> allProducts = productService.getAllProducts(pageable);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping
    public ResponseEntity<GetProductResponseDTO> getProductByName(@RequestParam String name) {
        GetProductResponseDTO getProductResponseDTO = productService.getProduct(name);
        return ResponseEntity.ok(getProductResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponseDTO> getProduct(@PathVariable Long id) {
        GetProductResponseDTO getProductResponseDTO = productService.getProduct(id);
        return ResponseEntity.ok(getProductResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CreateProductResponseDTO> createProduct(@Valid @RequestBody CreateProductRequestDTO body) {
        CreateProductResponseDTO createProductResponseDTO = productService.createProduct(body.name(), body.price(), body.category(), body.description());
        URI location = URI.create("product/" + createProductResponseDTO.id());
        return ResponseEntity.created(location).body(createProductResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequestDTO body) {
        if (body.price() == null && body.description() == null)
            throw new AllFieldsAreNullException("Both price and description are null!");

        UpdateProductResponseDTO updateProductResponseDTO = productService.updateProduct(id, body.price(), body.description());
        return ResponseEntity.ok(updateProductResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable Long id) {
        DeleteProductResponseDTO deleteProductResponseDTO = productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
