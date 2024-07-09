package com.example.productcatalogservice.repositories;

import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void testCategoryRepository() {
        Optional<Category> categoryOptional = categoryRepository.findById(10L);
        if (categoryOptional.isEmpty()) {
            return;
        }
        Category category = categoryOptional.get();
        System.out.println(category.getName());
        List<Product> productList = category.getProductList();
        for (Product product : productList) {
            System.out.println(product.getName());
        }
    }
}