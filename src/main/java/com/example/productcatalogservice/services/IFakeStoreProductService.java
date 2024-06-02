package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IFakeStoreProductService {
    List<Product> getProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);
}
