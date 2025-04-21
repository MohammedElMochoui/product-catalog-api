package com.example.product_catalog_api.controller;

import com.example.product_catalog_api.DTO.request.CreateProductRequestDTO;
import com.example.product_catalog_api.DTO.request.UpdateProductRequestDTO;
import com.example.product_catalog_api.DTO.response.CreateProductResponseDTO;
import com.example.product_catalog_api.DTO.response.DeleteProductResponseDTO;
import com.example.product_catalog_api.DTO.response.GetProductResponseDTO;
import com.example.product_catalog_api.DTO.response.UpdateProductResponseDTO;
import com.example.product_catalog_api.exception.AllFieldsAreNullException;
import com.example.product_catalog_api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    ResponseEntity<?> getAllProducts(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "name", required = false) String name
    ) {
        Object result = null;

        if (id != null) {
            result = productService.getProduct(id);
        } else if (name != null && !name.isBlank()) {
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
    ResponseEntity<CreateProductResponseDTO> createProduct(@RequestBody CreateProductRequestDTO body) {
        CreateProductResponseDTO createProductResponseDTO = productService.createProduct(body.name(), body.price(), body.category(), body.description());
        URI location = URI.create("product/" + createProductResponseDTO.id());
        return ResponseEntity.created(location).body(createProductResponseDTO);
    }

    @PatchMapping("/{id}")
    ResponseEntity<UpdateProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequestDTO body) {
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
