package com.ohgiraffers.inheritance.chap01.section02.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

/*
 * 📌 자식 클래스: FoodProduct
 */
@Entity
@DiscriminatorValue("FOOD")
public class FoodProduct extends Product {
    private LocalDate expirationDate; // 유통기한
    private boolean isOrganic; // 유기농 여부
    private String storageInstruction; // 보관 방법

    protected FoodProduct() {}

    public FoodProduct(String name, double price, String brand, int stockQuantity, LocalDate expirationDate, boolean isOrganic, String storageInstruction) {
        super(name, price, brand, stockQuantity);
        this.expirationDate = expirationDate;
        this.isOrganic = isOrganic;
        this.storageInstruction = storageInstruction;
    }

    @Override
    public String toString() {
        return "FoodProduct{" + super.toString() + ", expirationDate=" + expirationDate + ", isOrganic=" + isOrganic + ", storageInstruction='" + storageInstruction + "'}";
    }
}
