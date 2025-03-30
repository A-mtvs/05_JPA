package com.ohgiraffers.inheritance.chap01.section02.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;



/*
 * 📌 자식 클래스: ElectronicProduct
 */
@Entity
@DiscriminatorValue("ELECTRONIC")
public class ElectronicProduct extends Product {
    private int warrantyPeriod; // 보증 기간 (개월)
    private String powerConsumption; // 전력 소비량

    protected ElectronicProduct() {}

    public ElectronicProduct(String name, double price, String brand, int stockQuantity, int warrantyPeriod, String powerConsumption) {
        super(name, price, brand, stockQuantity);
        this.warrantyPeriod = warrantyPeriod;
        this.powerConsumption = powerConsumption;
    }

    @Override
    public String toString() {
        return "ElectronicProduct{" + super.toString() + ", warrantyPeriod=" + warrantyPeriod + ", powerConsumption='" + powerConsumption + "'}";
    }
}
