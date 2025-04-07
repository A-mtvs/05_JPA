package com.ohgiraffers.inheritance.chap01.section03.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/*
 * 📌 자식 클래스: ClothingProductJoined
 * @DiscriminatorValue("CLOTHING")
 * 조인 전략에서 @DiscriminatorValue("CLOTHING")는 해당 하위 클래스의 엔티티가 데이터베이스에 저장될 때 product_type 컬럼에 저장될 특정 값을 정의하며, 
 * JPA는 이 값을 기반으로 어떤 하위 클래스의 데이터를 조인하여 로드할지 결정
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

