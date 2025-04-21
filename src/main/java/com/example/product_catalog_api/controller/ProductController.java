package com.example.product_catalog_api.controller;

import com.example.product_catalog_api.DTO.request.CreateProductRequestDTO;
import com.example.product_catalog_api.DTO.request.UpdateProductRequestDTO;
import com.example.product_catalog_api.DTO.response.*;
import com.example.product_catalog_api.exception.AllFieldsAreNullException;
import com.example.product_catalog_api.service.ProductService;
import jakarta.validation.Valid;
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

//    @GetMapping("/{id}")
//    ResponseEntity<GetProductResponseDTO> getProductById(@PathVariable Long id) {
//        GetProductResponseDTO result = productService.getProduct(id);
//        return ResponseEntity.ok(result);
//    }

//    @GetMapping
//    ResponseEntity<GetProductsResponseDTO> getAllProducts() {
//        GetProductsResponseDTO result = productService.getAllProducts();
//        return ResponseEntity.ok(result);
//    }

    @GetMapping
    ResponseEntity<?> getProductByName(@RequestParam(name = "name", required = false) String name) {
        Object result = null;
        if (name != null) {
            result = productService.getProduct(name);
        } else {
            result = productService.getAllProducts();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    ResponseEntity<GetProductResponseDTO> getProduct(@PathVariable Long id) {
        GetProductResponseDTO getProductResponseDTO = productService.getProduct(id);
        return ResponseEntity.ok(getProductResponseDTO);
    }

    @PostMapping
    ResponseEntity<CreateProductResponseDTO> createProduct(@Valid @RequestBody CreateProductRequestDTO body) {
        CreateProductResponseDTO createProductResponseDTO = productService.createProduct(body.name(), body.price(), body.category(), body.description());
        URI location = URI.create("product/" + createProductResponseDTO.id());
        return ResponseEntity.created(location).body(createProductResponseDTO);
    }

    @PatchMapping("/{id}")
    ResponseEntity<UpdateProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequestDTO body) {
        if (body.price() == null && body.description() == null)
            throw new AllFieldsAreNullException("Both price and description are null!");

        UpdateProductResponseDTO updateProductResponseDTO = productService.updateProduct(id, body.price(), body.description());
        return ResponseEntity.ok(updateProductResponseDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable Long id) {
        DeleteProductResponseDTO deleteProductResponseDTO = productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
