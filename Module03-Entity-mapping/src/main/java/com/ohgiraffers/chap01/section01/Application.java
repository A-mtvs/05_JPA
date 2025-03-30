package com.ohgiraffers.chap01.section01;


import com.ohgiraffers.chap01.section01.model.Customer;
import com.ohgiraffers.chap01.section01.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/*
 * 🎯 ManyToOne 단방향 매핑
 * - 여러 주문이 하나의 고객에게 귀속되는 관계를 표현한다.
 * - 연관관계의 주인은 외래키를 가진 쪽이다 → Order
 * Customer -> Order
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Customer customer = new Customer("홍길동");
            em.persist(customer);

            Order order1 = new Order(customer);
            em.persist(order1);

            tx.commit();
            // 엔티티 매니저의 영속성 컨텍스트를 초기화
            em.clear();// 지연로딩 확인
            Order foundOrder = em.find(Order.class, order1.getId());
            System.out.println("=== Customer 조회 이전 == ");
            // 조회 시점에 customer를 조회함.
            System.out.println("지연 로딩 테스트: " + foundOrder);

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}