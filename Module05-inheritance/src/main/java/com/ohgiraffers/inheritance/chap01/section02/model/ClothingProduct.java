package com.ohgiraffers.inheritance.chap01.section02.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/*
 * 📌 자식 클래스: ClothingProduct
 */
@Entity
@DiscriminatorValue("CLOTHING")
public class ClothingProduct extends Product {
    private String size; // 사이즈 (S, M, L 등)
    private String material; // 소재
    private String color; // 색상

    protected ClothingProduct() {}

    public ClothingProduct(String name, double price, String brand, int stockQuantity, String size, String material, String color) {
        super(name, price, brand, stockQuantity);
        this.size = size;
        this.material = material;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ClothingProduct{" + super.toString() + ", size='" + size + "', material='" + material + "', color='" + color + "'}";
    }
}
