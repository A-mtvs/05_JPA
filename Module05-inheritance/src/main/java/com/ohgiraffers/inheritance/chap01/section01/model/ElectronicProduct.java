package com.ohgiraffers.inheritance.chap01.section01.model;

public class ElectronicProduct {
    private String name;
    private double price;
    private String brand;
    private int stockQuantity;
    private int warrantyPeriod; // 보증 기간 (개월)

    public ElectronicProduct(String name, double price, String brand, int stockQuantity, int warrantyPeriod) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.warrantyPeriod = warrantyPeriod;
    }


    @Override
    public String toString() {
        return "ElectronicProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", warrantyPeriod=" + warrantyPeriod +
                '}';
    }
}
