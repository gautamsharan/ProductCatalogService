package com.example.productcatalogservice.repositories;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @NonNull
    List<Product> findAll();

    @NonNull
    Optional<Product> findById(@NonNull Long id);

    @NonNull
    <S extends Product> S save(@NonNull S product);

    void deleteById(@NonNull Long id);
}
