package com.ohgiraffers.inheritance.chap01.section04.model;


import jakarta.persistence.*;

/*
 * 📌 부모 클래스: ProductTPC
 * TPC는 "Table Per Class"
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ProductTPC {

    // IDENTITY 사용시 에러 발생.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq", allocationSize = 1)

    private Long id;

    private String name;
    private double price;
    private String brand;
    private int stockQuantity;

    protected ProductTPC() {}

    public ProductTPC(String name, double price, String brand, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "ProductTPC{id=" + id + ", name='" + name + "', price=" + price + ", brand='" + brand + "', stockQuantity=" + stockQuantity + "}";
    }
}
