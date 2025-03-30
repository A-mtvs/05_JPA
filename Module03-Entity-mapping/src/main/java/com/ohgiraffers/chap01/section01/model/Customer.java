package com.ohgiraffers.chap01.section01.model;

import jakarta.persistence.*;

/*
 * 엔티티 이름은 데이터베이스에서 이 엔티티와 관련된 테이블을 식별하는 데 사용된다.
 *
 * - name 속성은 엔티티의 식별자를 지정하며, JPQL 쿼리에서 이 이름을 사용하여 엔티티를 참조할 수 있다.
 * - 기본적으로 클래스 이름이 엔티티 이름으로 사용되지만, name 속성을 통해 다른 이름을 지정할 수 있다.
* */
@Entity(name = "section01-customer")
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "name")
    private String name;

    protected Customer() {}

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "고객 id=" + id +
                ", 이름 ='" + name + '\'' +
                '}';
    }
}