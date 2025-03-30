package com.ohgiraffers.inheritance.chap01.section02.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String brand;
    private int stockQuantity;

    protected Product() {}

    public Product(String name, double price, String brand, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", brand='" + brand + "', stockQuantity=" + stockQuantity + "}";
    }

}
