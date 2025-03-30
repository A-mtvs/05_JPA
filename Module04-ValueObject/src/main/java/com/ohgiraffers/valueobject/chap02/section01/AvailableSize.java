package com.ohgiraffers.valueobject.chap02.section01;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "section01_available")
@Table(name = "product_available_sizes_section01")
public class AvailableSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "label")
    private String label; // S, M, L, XL 등 사이즈 라벨

    @Column(name = "stockQuantity")
    private int stockQuantity; // 해당 사이즈의 재고 수량

    protected AvailableSize() {}

    /*
     * AvailableSize 생성자
     * - 사이즈 라벨과 초기 재고 수량을 인자로 받습니다.
     * - 사이즈 라벨은 필수이며, 재고 수량은 0 이상이어야 합니다.
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

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /*
     * decreaseStock 메서드
     * - 해당 사이즈의 재고 수량을 주어진 quantity만큼 감소시킵니다.
     * - 재고가 부족하면 IllegalArgumentException을 발생시킵니다.
     */
    public void decreaseStock(int quantity) {
        if (this.stockQuantity - quantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stockQuantity -= quantity;
    }


    @Override
    public String toString() {
        return label + " (재고: " + stockQuantity + ")";
    }
}