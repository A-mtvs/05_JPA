package com.ohgiraffers.inheritance.chap01.section01.model;

public class ClothingProduct {
    private String name;
    private double price;
    private String brand;
    private int stockQuantity;
    private String size; // 사이즈 (S, M, L 등)
    private String material; // 소재

    public ClothingProduct(String name, double price, String brand, int stockQuantity, String size, String material) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.size = size;
        this.material = material;
    }

    @Override
    public String toString() {
        return "ClothingProduct{name='" + name + "', price=" + price + ", brand='" + brand + "', stockQuantity=" + stockQuantity + ", size='" + size + "', material='" + material + "'}";
    }
}
