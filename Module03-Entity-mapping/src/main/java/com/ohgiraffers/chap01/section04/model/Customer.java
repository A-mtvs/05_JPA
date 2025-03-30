package com.ohgiraffers.chap01.section04.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
 * 📌 이 클래스는 고객 정보를 표현하는 Entity이다.
 * 고객은 여러 건의 주문을 할 수 있기 때문에 1:N 관계로 매핑한다.
 * 객체지향의 입장에서 보면 Customer는 Order를 포함하고 있지만,
 * 실제 외래키는 Order 테이블에 존재하게 된다. (연관관계의 주인은 Order)
 *
 * customer(1) <- (n)order(1) - (1)delivery
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성 (DB의 AUTO_INCREMENT 전략)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    // JPA는 기본 생성자를 필수로 요구함 (public 또는 protected)
    protected Customer() {}

    public Customer(String name) {
        this.name = name;
    }

    // 양방향 연관관계를 설정하는 편의 메서드
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);  // Order에서도 Customer를 설정해줘야 연관관계가 완성됨
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
}
