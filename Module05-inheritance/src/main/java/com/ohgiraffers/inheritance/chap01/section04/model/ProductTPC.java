package com.ohgiraffers.inheritance.chap01.section04.model;


import jakarta.persistence.*;

/*
 * 📌 부모 클래스: ProductTPC
 * - InheritanceType.TABLE_PER_CLASS 전략은 상속 계층의 각 클래스마다 별도의 테이블을 생성한다.
 * - 부모 클래스인 ProductTPC의 속성들은 ProductTPC에 해당하는 테이블에 저장되고,
 * - 각 자식 클래스의 속성들은 각각의 테이블에 저장된다.
 * - 이때, 각 자식 테이블은 부모 클래스의 모든 속성을 포함하여 독립적인 구조를 가진다.
 * - 즉, 상속된 속성들이 각 자식 클래스의 테이블에 중복되어 저장된다.
 * - 부모 클래스 타입으로 조회할 경우, 모든 자식 테이블을 UNION ALL 연산을 통해 통합하여 결과를 얻는다.
 * - 이 전략은 데이터의 물리적인 분리를 명확하게 하고, 특정 하위 클래스의 데이터를 조회할 때 효율적일 수 있지만,
 * - 전체 상속 구조에 대한 조회가 필요할 경우 성능상의 이슈가 발생할 수 있으며, 데이터 중복으로 인해 관리상의 어려움이 있을 수 있다.
 * 
 * `UNION ALL`은 컬럼 이름을 기준으로 데이터를 합치는 것이 아니라, `SELECT` 문에서 **컬럼이 나타나는 순서**를 기준으로 데이터를 결합
 * 만약 쿼리의 길이가 다르면 오류가 발생한다.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ProductTPC {

    // IDENTITY 사용시 에러 발생.
    /*
     IDENTITY 사용시 에러 발생.
     TABLE_PER_CLASS 전략은 각 자식 클래스마다 별도의 테이블을 생성다.
     IDENTITY 전략은 각 테이블 내에서 독립적인 자동 증가 ID를 생성하므로,
     서로 다른 자식 테이블에 저장된 엔티티들이 동일한 ID 값을 가질 수 있다.
     이는 상속 관계에서 엔티티의 고유성을 보장하는 데 문제를 일으키므로,
     TABLE_PER_CLASS 전략에서는 SEQUENCE 전략과 같은 공유된 ID 생성 방식을 사용하는 것이 일반적이다.
    */
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
