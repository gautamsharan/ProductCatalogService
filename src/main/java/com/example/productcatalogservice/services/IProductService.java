package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Long productId, Product product);

    Product deleteProduct(Long id);
}
