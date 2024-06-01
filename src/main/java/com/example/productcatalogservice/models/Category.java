package com.example.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Category extends BaseModel{
    private String name;

    private String description;

    private List<Product> productList = new ArrayList<>();
}
