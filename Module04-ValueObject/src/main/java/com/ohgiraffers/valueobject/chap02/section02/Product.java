package com.ohgiraffers.valueobject.chap02.section02;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
 * 📌 @Entity
 * - Product 클래스를 JPA 엔티티로 지정합니다.
 * - 데이터베이스의 'products' 테이블과 매핑됩니다.
 */
@Entity(name = "section02_product_entity")
@Table(name = "products")
public class Product {

    /*
     * 📌 @Id
     * - id 필드를 엔티티의 기본 키로 지정합니다.
     * 📌 @GeneratedValue(strategy = GenerationType.IDENTITY)
     * - 기본 키 생성 전략을 데이터베이스의 자동 증가 기능을 사용하도록 설정합니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    /*
     * 📌 @ElementCollection
     * - Product 엔티티가 AvailableSize 타입의 값 객체 컬렉션을 가짐을 명시
     * - 하나의 상품이 여러 개의 가능한 사이즈를 가질 수 있다.
     *
     * 📌 @CollectionTable(name = "product_available_sizes", joinColumns = @JoinColumn(name = "product_id"))
     * - 'availableSizes' 컬렉션을 저장할 별도의 테이블을 'product_available_sizes'로 지정한다.
     * - 'product_id' 컬럼을 외래 키로 사용하여 Product 엔티티의 'id'와 연결한다.
     */
    @ElementCollection
    @CollectionTable(name = "product_available_sizes", joinColumns = @JoinColumn(name = "product_id"))
    private List<AvailableSize> availableSizes = new ArrayList<>();

    protected Product() {}

    /*
     * Product 엔티티 생성자
     * - 상품 이름을 인자로 받아 Product 객체를 생성
     */
    public Product(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<AvailableSize> getAvailableSizes() {
        return availableSizes;
    }

    /*
     * addAvailableSize 메서드
     * - 상품에 새로운 가능한 사이즈를 추가
     * - AvailableSize 값 객체를 컬렉션에 추가
     */
    public void addAvailableSize(AvailableSize availableSize) {
        this.availableSizes.add(availableSize);
    }

    /*
     * removeAvailableSize 메서드
     * - 상품에서 특정 사이즈를 제거
     * - equals 메서드를 사용하여 컬렉션에서 해당 AvailableSize 객체를 찾아 제거.
     */
    public void removeAvailableSize(AvailableSize availableSize) {
        this.availableSizes.removeIf(size -> size.equals(availableSize));
    }

    /*
     * decreaseStock 메서드
     * - 특정 사이즈의 재고 수량을 감소
     * - 컬렉션에서 해당 사이즈를 찾고, 재고 감소 로직을 수행
     * - 해당 사이즈가 없으면 IllegalArgumentException을 발생
     */
    public void decreaseStock(String sizeLabel, int quantity) {
        for (AvailableSize size : availableSizes) {
            if (size.getLabel().equals(sizeLabel)) {
                size.decreaseStock(quantity);
                return;
            }
        }
        throw new IllegalArgumentException("해당 사이즈의 상품이 없습니다: " + sizeLabel);
    }
}