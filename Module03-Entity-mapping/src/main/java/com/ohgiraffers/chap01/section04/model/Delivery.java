package com.ohgiraffers.chap01.section04.model;

import jakarta.persistence.*;

/*
 * 📌 배송 정보를 나타내는 Entity이며,
 * 주문(Order)과 1:1 관계로 설정됨.
 * 배송은 특정 주문에 대해 한 번만 발생할 수 있기 때문에 일대일(1:1) 관계를 사용한다.
 */
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;


    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "address", nullable = false)
    private String address;

    /*
     * 배송 상태(status)는 일반 문자열로 관리하지만, Enum으로 대체 가능하다.
     * 예: READY, SHIPPED, DELIVERED
     */
    @Column(name = "status", nullable = false)
    private String status;

    public Delivery() {}

    public Delivery(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "order=" + order +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
