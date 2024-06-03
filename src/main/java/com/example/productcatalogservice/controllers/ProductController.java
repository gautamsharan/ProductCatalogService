package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IFakeStoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    IFakeStoreProductService iFakeStoreProductService;

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        try {
            if (productId <= 0 || productId > 20) {
                throw new IllegalArgumentException("Invalid product id");
            }
            Product product = iFakeStoreProductService.getProductById(productId);
            ProductDto body = getProductDtoFromProduct(product);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("source", "fake-store.api");
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        try {
            Product product = iFakeStoreProductService.createProduct(productDto);
            if (product == null) {
                throw new RuntimeException("Could not create product");
            }
            ProductDto body = getProductDtoFromProduct(product);
            return new ResponseEntity<>(body, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    private ProductDto getProductDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setDescription(product.getCategory().getDescription());
        productDto.setCategory(categoryDto);
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }
}
