package com.ohgiraffers.chap01.section03.model;

import jakarta.persistence.*;

@Entity(name = "section03-Delivery")
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @Column(name = "address", nullable = false)
    private String address;

    /*
     * 📌 연관관계의 주인: Delivery → Order
     * - Delivery가 외래키를 가지므로 주인이다
     * - @OneToOne: 일대일 단방향 매핑
     * - @JoinColumn: 외래키가 위치할 컬럼 이름 설정
     */
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /*
     * 배송 상태(status)는 일반 문자열로 관리하지만, Enum으로 대체 가능하다.
     * 예: READY, SHIPPED, DELIVERED
     */
    @Column(name = "status", nullable = false)
    private String status;


    protected Delivery() {}

    public Delivery(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Order getOrder() {
        return order;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", 주문 =" + order.getId() +
                ", status='" + status + '\'' +
                '}';
    }
}