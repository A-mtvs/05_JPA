package com.ohgiraffers.inheritance.chap01.section03.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/*
 * 📌 자식 클래스: ElectronicProductJoined
 */
@Entity
@Table(name = "electronic_products_joined")
@DiscriminatorValue("ELECTRONIC")
public class ElectronicProductJoined extends ProductJoined {
    private int warrantyPeriod;
    private String powerConsumption;

    protected ElectronicProductJoined() {}

    public ElectronicProductJoined(String name, double price, String brand, int stockQuantity, int warrantyPeriod, String powerConsumption) {
        super(name, price, brand, stockQuantity);
        this.warrantyPeriod = warrantyPeriod;
        this.powerConsumption = powerConsumption;
    }

    @Override
    public String toString() {
        return "ElectronicProductJoined{" + super.toString() + ", warrantyPeriod=" + warrantyPeriod + ", powerConsumption='" + powerConsumption + "'}";
    }
}