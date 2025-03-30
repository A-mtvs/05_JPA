package com.ohgiraffers.inheritance.chap01.section03.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/*
 * 📌 자식 클래스: ClothingProductJoined
 */
@Entity
@Table(name = "clothing_products_joined")
@DiscriminatorValue("CLOTHING")
public class ClothingProductJoined extends ProductJoined {
    private String size;
    private String material;
    private String color;

    protected ClothingProductJoined() {}

    public ClothingProductJoined(String name, double price, String brand, int stockQuantity, String size, String material, String color) {
        super(name, price, brand, stockQuantity);
        this.size = size;
        this.material = material;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ClothingProductJoined{" + super.toString() + ", size='" + size + "', material='" + material + "', color='" + color + "'}";
    }
}

