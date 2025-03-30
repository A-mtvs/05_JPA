package com.ohgiraffers.chap01.section04.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
 * 📌 이 클래스는 주문(Order)을 나타내는 Entity이며,
 * 고객(Customer)과 1:N 관계로 연결되고, 배송(Delivery)과 1:1 관계로 연결된다.
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /*
     * 📌 배송과의 1:1 양방향 연관관계 설정
     * - Order는 연관관계의 주인이 아님 (mappedBy 사용)
     * - cascade 설정으로 주문 저장 시 배송도 같이 저장됨
     * - orphanRemoval 설정으로 배송 삭제도 자동 수행 가능
     */
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER) // N+1을 위한 Eager
    private Delivery delivery;

    /*
     * 주문 날짜: 기본값으로 현재 시간 저장
     * LocalDateTime은 날짜/시간 정보를 갖는 Java 8 이상 타입
     */
    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // 연관된 배송 정보를 세팅하면서, 반대편 연관관계도 함께 설정
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this); // Delivery 측에서도 Order를 설정해줘야 양방향 연결이 완성됨
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "주문 번호 =" + id +
                " 주문 시간 =" + orderDate +
                '}';
    }
}
