package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
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
    IProductService iProductService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() throws RuntimeException {
        List<ProductDto> results = new ArrayList<>();
        List<Product> products = iProductService.getProducts();
        for (Product product : products) {
            results.add(getProductDtoFromProduct(product));
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) throws IllegalArgumentException {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product id");
        }
        Product product = iProductService.getProductById(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ProductDto body = getProductDtoFromProduct(product);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("source", "mysql");
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) throws RuntimeException {
        Product product = getProductFromProductDto(productDto);
        Product newProduct = iProductService.createProduct(product);
        if (newProduct == null) {
            throw new RuntimeException("Could not create product");
        }
        ProductDto body = getProductDtoFromProduct(newProduct);
        return new ResponseEntity<>(body, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) throws RuntimeException {
        Product product = getProductFromProductDto(productDto);
        Product newProduct = iProductService.updateProduct(productId, product);
        if (newProduct == null) {
            throw new RuntimeException("Product does not exists");
        }
        ProductDto body = getProductDtoFromProduct(newProduct);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) throws IllegalArgumentException {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product id");
        }
        Product product = iProductService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with id: " + productId + " does not exist.");
        }
        iProductService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ProductDto getProductDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product getProductFromProductDto(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
