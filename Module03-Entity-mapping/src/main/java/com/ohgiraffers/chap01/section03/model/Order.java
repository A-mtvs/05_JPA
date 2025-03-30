package com.ohgiraffers.chap01.section03.model;


import com.ohgiraffers.chap01.section02.model.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "section03-order")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "customer_id")
    private int customer;

    /*
     * 📌 1:1 양방향 연관관계 매핑
     * - 주문(Order)은 하나의 배송(Delivery)을 가질 수 있고, 배송은 하나의 주문에 속한다 (1:1).
     * - mappedBy: 연관관계의 주인이 되는 엔티티에서 정의한 필드의 이름을 명시한다.
     *             즉, Delivery의 order(fk)를 지정함.
     * - 연관관계 주인은 외래 키(FK)를 가지고 있으며, 해당 관계의 상태를 직접적으로 관리하는 것을 의미한다.
     */
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {
    }

    public Order(int customer) {
        this.customer = customer;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this); // 양방향 설정
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", delivery=" + delivery +
                '}';
    }
}
