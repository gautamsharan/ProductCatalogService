package com.example.productcatalogservice.repositories;

import com.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    List<Product> findByPriceBetween(@NonNull Double lowerBound, @NonNull Double upperBound);

    @Query("SELECT p.name from Product as p where p.id=?1")
    String findProductNameById(@NonNull Long id);

    List<Product> findByName(String query);
}
