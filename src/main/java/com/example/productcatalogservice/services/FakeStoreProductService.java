package com.example.productcatalogservice.services;

import com.example.productcatalogservice.clients.FakeStoreClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private FakeStoreClient fakeStoreClient;

    @Override
    public List<Product> getProducts() {
        FakeStoreProductDto[] fakeStoreProductDtoArray = fakeStoreClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtoArray) {
            products.add(getProductFromFakeStoreProductDto(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product getProductById(Long productId) {
        return getProductFromFakeStoreProductDto(fakeStoreClient.getProduct(productId));
    }

    @Override
    public Product createProduct(Product product) {
        return getProductFromFakeStoreProductDto(fakeStoreClient.createProduct(getFakeStoreProductFromProduct(product)));
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return getProductFromFakeStoreProductDto(fakeStoreClient.updateProduct(productId, getFakeStoreProductFromProduct(product)));
    }

    @Override
    public void deleteProduct(Long productId) {
        getProductFromFakeStoreProductDto(fakeStoreClient.deleteProduct(productId));
    }

    private Product getProductFromFakeStoreProductDto(@Nullable FakeStoreProductDto fakeStoreProductDto) {
        if (fakeStoreProductDto == null) {
            return null;
        }
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

    private FakeStoreProductDto getFakeStoreProductFromProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setImage(product.getImageUrl());
        return fakeStoreProductDto;
    }
}
