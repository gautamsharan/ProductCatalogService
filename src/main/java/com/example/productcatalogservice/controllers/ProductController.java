package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IFakeStoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IFakeStoreProductService iFakeStoreProductService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() throws RuntimeException {
        List<ProductDto> results = new ArrayList<>();
        List<Product> products = iFakeStoreProductService.getProducts();
        for (Product product : products) {
            results.add(getProductDtoFromProduct(product));
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) throws IllegalArgumentException {
        if (productId <= 0 || productId > 20) {
            throw new IllegalArgumentException("Invalid product id");
        }
        Product product = iFakeStoreProductService.getProductById(productId);
        ProductDto body = getProductDtoFromProduct(product);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("source", "fake-store.api");
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) throws RuntimeException {
        Product product = getProductFromProductDto(productDto);
        Product newProduct = iFakeStoreProductService.createProduct(product);
        if (newProduct == null) {
            throw new RuntimeException("Could not create product");
        }
        ProductDto body = getProductDtoFromProduct(newProduct);
        return new ResponseEntity<>(body, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) throws RuntimeException {
        Product product = getProductFromProductDto(productDto);
        Product newProduct = iFakeStoreProductService.updateProduct(productId, product);
        if (newProduct == null) {
            throw new RuntimeException("Could not update product");
        }
        ProductDto body = getProductDtoFromProduct(newProduct);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Long productId) throws IllegalArgumentException {
        Product product = iFakeStoreProductService.deleteProduct(productId);
        if (product == null) {
            throw new IllegalArgumentException("Invalid product id");
        }
        ProductDto body = getProductDtoFromProduct(product);
        return new ResponseEntity<>(body, HttpStatus.OK);
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

    private Product getProductFromProductDto(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        Category category = new Category();
        category.setName(productDto.getCategory().getName());
        category.setDescription(productDto.getCategory().getDescription());
        product.setCategory(category);
        return product;
    }
}
