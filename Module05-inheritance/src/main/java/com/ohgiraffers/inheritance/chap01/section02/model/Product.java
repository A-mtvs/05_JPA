package com.ohgiraffers.inheritance.chap01.section02.model;

import jakarta.persistence.*;

/**
 * @Inheritance(strategy = InheritanceType.SINGLE_TABLE):
 * - 이 애노테이션은 엔티티 상속 전략을 정의한다.
 * - InheritanceType.SINGLE_TABLE 전략은 상속 계층의 모든 클래스를 하나의 테이블에 매핑한다.
 * - 즉, Product 클래스와 그 하위 클래스들의 모든 속성이 'products' 테이블이라는 하나의 테이블에 저장된다.
 *
 * @DiscriminatorColumn(name = "product_type"):
 * - 이 애노테이션은 SINGLE_TABLE 상속 전략에서 사용되며, 레코드의 타입을 구분하는 컬럼을 지정한다.
 * - 'product_type'이라는 컬럼이 'products' 테이블에 생성되어, 각 레코드가 어떤 하위 클래스의 인스턴스인지 나타내는 값을 저장한다.
 * - 예를 들어, Book 클래스가 Product를 상속받는다면, 'product_type' 컬럼에 'Book'과 같은 값이 저장될 수 있다.
 */

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
