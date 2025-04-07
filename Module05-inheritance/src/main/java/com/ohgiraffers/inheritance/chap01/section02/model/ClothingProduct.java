package com.ohgiraffers.inheritance.chap01.section02.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/*
 * 📌 자식 클래스: ClothingProduct
 * @DiscriminatorValue("CLOTHING"): 이 애노테이션은 상속 전략이 SINGLE_TABLE일 때 사용되며,
 * Product 클래스를 상속받는 ClothingProduct 클래스의 레코드가
 * 데이터베이스 테이블의 'product_type' 컬럼에 "CLOTHING"이라는 값으로 저장되도록 지정한다.
 * 이를 통해 데이터베이스에서 해당 레코드가 ClothingProduct 타입임을 식별할 수 있다.
 */
@Entity
@DiscriminatorValue("CLOTHING")
public class ClothingProduct extends Product {
    private String size; // 사이즈 (S, M, L 등)
    private String material; // 소재
    private String color; // 색상

    protected ClothingProduct() {}

    public ClothingProduct(String name, double price, String brand, int stockQuantity, String size, String material, String color) {
        super(name, price, brand, stockQuantity);
        this.size = size;
        this.material = material;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ClothingProduct{" + super.toString() + ", size='" + size + "', material='" + material + "', color='" + color + "'}";
    }
}
