package com.mtiengo.api.models;

public record Product(
        Integer id,
        String title,
        String description,
        String category,
        Double price,
        Double discountPercentage,
        Double rating,
        Integer stock,
        String brand,
        String sku
) {}
