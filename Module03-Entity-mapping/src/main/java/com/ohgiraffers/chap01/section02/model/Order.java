package com.ohgiraffers.chap01.section02.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "section02-orders")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", 고객명 =" + customer.getName() +
                ", orderDate=" + orderDate +
                '}';
    }
}