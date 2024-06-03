package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements IFakeStoreProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if (response.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDto != null) {
            return getProductFromFakeStoreProductDto(fakeStoreProductDto);
        }

        return null;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        FakeStoreProductDto fakeStoreProductDto = getFakeStoreProductFromProductDto(productDto);
        HttpEntity<FakeStoreProductDto> request = new HttpEntity<>(fakeStoreProductDto);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity("https://fakestoreapi.com/products", request, FakeStoreProductDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            FakeStoreProductDto responseBody = response.getBody();
            if (responseBody != null) {
                return getProductFromFakeStoreProductDto(responseBody);
            }
        }

        return null;
    }

    private Product getProductFromFakeStoreProductDto(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    private FakeStoreProductDto getFakeStoreProductFromProductDto(ProductDto productDto) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(productDto.getName());
        fakeStoreProductDto.setPrice(productDto.getPrice());
        fakeStoreProductDto.setDescription(productDto.getDescription());
        fakeStoreProductDto.setCategory(productDto.getCategory().getName());
        fakeStoreProductDto.setImage(productDto.getImageUrl());
        return fakeStoreProductDto;
    }
}
