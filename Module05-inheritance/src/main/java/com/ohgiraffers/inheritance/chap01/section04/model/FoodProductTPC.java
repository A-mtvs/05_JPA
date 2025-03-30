package com.ohgiraffers.inheritance.chap01.section04.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

/*
 * 📌 자식 클래스: FoodProductTPC
 */
@Entity
@Table(name = "food_products_tpc")
public class FoodProductTPC extends ProductTPC {
    private LocalDate expirationDate;
    private boolean isOrganic;
    private String storageInstruction;

    protected FoodProductTPC() {}

    public FoodProductTPC(String name, double price, String brand, int stockQuantity, LocalDate expirationDate, boolean isOrganic, String storageInstruction) {
        super(name, price, brand, stockQuantity);
        this.expirationDate = expirationDate;
        this.isOrganic = isOrganic;
        this.storageInstruction = storageInstruction;
    }

    @Override
    public String toString() {
        return "FoodProductTPC{" + super.toString() + ", expirationDate=" + expirationDate + ", isOrganic=" + isOrganic + ", storageInstruction='" + storageInstruction + "'}";
    }
}
