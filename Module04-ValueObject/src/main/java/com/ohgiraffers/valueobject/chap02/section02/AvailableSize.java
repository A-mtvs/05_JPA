package com.ohgiraffers.valueobject.chap02.section02;


import jakarta.persistence.Embeddable;

import java.util.Objects;

/*
 * 📌 @Embeddable
 * - AvailableSize는 상품 엔티티에 포함되는 값 객체.
 * - 상품이 가질 수 있는 특정 사이즈와 그 재고 수량을 나타낸다.
 */
@Embeddable
public class AvailableSize {

    private String label; // S, M, L, XL 등 사이즈 라벨
    private int stockQuantity; // 해당 사이즈의 재고 수량

    protected AvailableSize() {}

    /*
     * AvailableSize 생성자
     * - 사이즈 라벨과 초기 재고 수량을 인자로 받는다
     * - 사이즈 라벨은 필수이며, 재고 수량은 0 이상이어야 한다.
     */
    public AvailableSize(String label, int stockQuantity) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("사이즈 라벨은 필수입니다.");
        }

        if (stockQuantity < 0) {
            throw new IllegalArgumentException("재고 수량은 0 이상이어야 합니다.");
        }
        this.label = label;
        this.stockQuantity = stockQuantity;
    }

    public String getLabel() {
        return label;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    /*
     * decreaseStock 메서드
     * - 해당 사이즈의 재고 수량을 주어진 quantity만큼 감소.
     * - 재고가 부족하면 IllegalArgumentException을 발생.
     */
    public void decreaseStock(int quantity) {
        if (this.stockQuantity - quantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stockQuantity -= quantity;
    }

    /*
     * equals 메서드 재정의
     * - 두 AvailableSize 객체의 사이즈 라벨이 같으면 true를 반환한다.
     * - 재고 수량은 동등성 비교에 영향을 주지 않는다.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableSize that = (AvailableSize) o;
        return Objects.equals(label, that.label); // 사이즈 라벨만 같으면 같은 사이즈로 취급
    }

    /*
     * hashCode 메서드 재정의
     * - equals 메서드를 재정의할 때는 hashCode 메서드도 함께 재정의.
     * - 사이즈 라벨의 해시 코드를 반환.
     */
    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return label + " (재고: " + stockQuantity + ")";
    }
}