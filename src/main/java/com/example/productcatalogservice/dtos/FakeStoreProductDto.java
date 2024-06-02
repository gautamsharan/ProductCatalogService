package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {

    private Long id;

    private String title;

    private String description;

    private String image;

    private Double price;

    private String category;

    private FakeStoreRatingDto rating;
}
