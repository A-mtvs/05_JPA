package com.ohgiraffers.inheritance.chap01.section01.model;

import java.time.LocalDate;

public class FoodProduct {
    private String name;
    private double price;
    private String brand;
    private int stockQuantity;
    private LocalDate expirationDate; // 유통기한
    private boolean isOrganic; // 유기농 여부

    public FoodProduct(String name, double price, String brand, int stockQuantity, LocalDate expirationDate, boolean isOrganic) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.expirationDate = expirationDate;
        this.isOrganic = isOrganic;
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", expirationDate=" + expirationDate +
                ", isOrganic=" + isOrganic +
                '}';
    }
}
