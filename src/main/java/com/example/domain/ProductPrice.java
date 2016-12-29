package com.example.domain;

public class ProductPrice {

    private String productName;

    private Double price;

    public ProductPrice(String productName, Double price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }
}
