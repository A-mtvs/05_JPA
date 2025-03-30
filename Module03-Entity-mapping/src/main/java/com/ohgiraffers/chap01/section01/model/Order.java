package com.ohgiraffers.chap01.section01.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "section01-orders")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    /*
     * 📌 @ManyToOne: 다대일 단방향 매핑 (주문 → 고객)
     * - 주문은 반드시 고객 한 명에게 속한다.
     * - 연관관계의 주인이므로 @JoinColumn으로 외래키를 명시함
     *
     * - 지연 로딩(LAZY): 실제 사용할 때 조회하도록 설정해 성능 최적화
     *   (초기에는 프록시 객체로 대기하다가, getCustomer() 호출 시 DB에서 조회)
     * - 즉시 로딩(EAGER): 연관된 엔티티를 즉시 조회하도록 설정
     *   (주문을 조회할 때 관련된 고객 정보도 함께 조회됨)
     *   예: @ManyToOne(fetch = FetchType.EAGER)
     *
     *   선택에 따라 성능과 메모리 사용량에 영향을 줄 수 있으므로,
     *   상황에 맞게 적절한 전략을 선택해야 함.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    protected Order() {}

    public Order(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", customerId=" + (customer != null ? customer : null) +
                '}';
    }

}