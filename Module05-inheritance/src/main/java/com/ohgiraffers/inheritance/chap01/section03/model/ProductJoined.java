package com.ohgiraffers.inheritance.chap01.section03.model;


import jakarta.persistence.*;
/*
 * 📌 부모 클래스: ProductJoined
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type") // 해당 컬럼의 값에 따라서 조인하는 테이블이 달라진다.
@Table(name = "products_joined")
public class ProductJoined {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String brand;
    private int stockQuantity;

    protected ProductJoined() {}

    public ProductJoined(String name, double price, String brand, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "ProductJoined{id=" + id + ", name='" + name + "', price=" + price + ", brand='" + brand + "', stockQuantity=" + stockQuantity + "}";
    }
}
