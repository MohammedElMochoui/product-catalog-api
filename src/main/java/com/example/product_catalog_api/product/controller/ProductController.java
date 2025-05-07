package com.example.product_catalog_api.product.controller;

import com.example.product_catalog_api.product.DTO.request.CreateProductRequestDTO;
import com.example.product_catalog_api.product.DTO.request.UpdateProductRequestDTO;
import com.example.product_catalog_api.product.DTO.response.CreateProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.DeleteProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.GetProductResponseDTO;
import com.example.product_catalog_api.product.DTO.response.UpdateProductResponseDTO;
import com.example.product_catalog_api.product.exception.AllFieldsAreNullException;
import com.example.product_catalog_api.product.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<GetProductResponseDTO>> getAllProducts(Pageable pageable, Principal p) {
        log.info("User: {}", p.getName());
        Page<GetProductResponseDTO> allProducts = productService.getAllProducts(pageable);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping(params = "name")
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
        URI location = URI.create("products/" + createProductResponseDTO.id());
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
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        DeleteProductResponseDTO deleteProductResponseDTO = productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
