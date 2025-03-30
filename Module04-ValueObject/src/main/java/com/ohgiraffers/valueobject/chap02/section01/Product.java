package com.ohgiraffers.valueobject.chap02.section01;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity(name = "section01_product")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableSize> availableSizes = new ArrayList<>();

    protected Product() {}

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
     * - 상품에 새로운 가능한 사이즈를 추가합니다.
     * - AvailableSize 엔티티를 컬렉션에 추가하고, AvailableSize에 Product를 설정합니다.
     */
    public void addAvailableSize(AvailableSize availableSize) {
        this.availableSizes.add(availableSize);
        availableSize.setProduct(this); // 양방향 연관 관계 설정
    }

    /*
     * removeAvailableSize 메서드
     * - 상품에서 특정 사이즈를 제거합니다.
     * - 컬렉션에서 해당 AvailableSize 엔티티를 제거하고, AvailableSize의 Product를 null로 설정합니다.
     */
    public void removeAvailableSize(AvailableSize availableSize) {
        this.availableSizes.remove(availableSize);
        availableSize.setProduct(null); // 양방향 연관 관계 해제
    }

    /*
     * decreaseStock 메서드
     * - 특정 사이즈 라벨을 가진 AvailableSize 엔티티를 찾아 재고를 감소시킵니다.
     * - 해당 사이즈가 없으면 IllegalArgumentException을 발생시킵니다.
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