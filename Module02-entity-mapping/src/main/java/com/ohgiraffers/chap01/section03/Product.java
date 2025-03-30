package com.ohgiraffers.chap01.section03;

import jakarta.persistence.*;

/*
 * 📌 Embedded 타입 예제: Product 엔티티와 Money 복합 값 타입
 *
 * ▶ 값 객체(Value Object)의 대표 사례로 "가격(Money)"을 구성하며, 실무에서 매우 유용한 패턴이다.
 *
 * ▶ 복합 값 타입 사용 목적:
 *  - 공통 필드를 하나의 객체로 묶어서 코드 재사용성을 높임
 *  - 엔티티와 값 타입의 역할을 분리 → 객체지향 설계에 적합
 *  - JPA가 내부적으로 해당 필드들을 컬럼으로 펼쳐서 저장
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /*
     * 📌 @Embedded 사용 예시
     * - 가격 정보는 단순 숫자(BigDecimal)로 처리할 수도 있지만,
     *   실무에서는 통화(currency), 할인 여부 등을 포함하는 구조가 유용함
     */
    @Embedded
    private Money price;

    /*
     * 📌 동일한 타입을 두 번 이상 @Embedded 할 경우
     * - 컬럼명이 충돌하므로 @AttributeOverrides를 사용해 필드명 재정의 할 수 잇다.
     * - 현재는 한번 호출되었으나 문법을 위해 작성함.
     */
    @Embedded
    @AttributeOverrides({
            // name : Embedded의 이름을 명시함.
            // column : 현재 테이블에 매핑할 값을 명시함.
            @AttributeOverride(name = "name", column = @Column(name = "manufacturer_name")),
            @AttributeOverride(name = "country", column = @Column(name = "manufacturer_country"))
    })
    private Manufacturer manufacturer;

    protected Product() {}

    public Product(String name, Money price, Manufacturer manufacturer) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}