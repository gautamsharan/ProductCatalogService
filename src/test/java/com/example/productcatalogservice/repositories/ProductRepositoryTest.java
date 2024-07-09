package com.example.productcatalogservice.repositories;

import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void testFindByPriceBetween() {
        List<Product> productList = productRepository.findByPriceBetween(49.0, 299.0);
        for (Product product : productList) {
            System.out.println(product.getName() + " " + product.getCategory().getName());
        }
        System.out.println(productRepository.findProductNameById(17L));
    }

}