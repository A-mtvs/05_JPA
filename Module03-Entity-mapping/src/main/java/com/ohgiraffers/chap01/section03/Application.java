package com.ohgiraffers.chap01.section03;

import com.ohgiraffers.chap01.section03.model.Delivery;
import com.ohgiraffers.chap01.section03.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


/*
 * 🎯 1:1 양방향 연관관계 매핑
 * - 주문(Order)은 하나의 배송(Delivery) 정보를 가질 수 있고, 각 배송은 하나의 주문에 귀속된다.
 * - 이 경우, Order 클래스에서 Delivery를 참조하고,
 *   Delivery 클래스에서 Order를 참조하여 1:1 양방향 관계를 형성한다.
 *
 * - 연관관계의 주인은 외래키(FK)를 가진 엔티티(Delivery)이며,
 *   Order는 `mappedBy`를 사용하여 연관관계의 주인이 아님을 명시한다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 고객과 fk가 설정되어 있어 고객 테이블에 존재하는 값 등록
            // 오류가 발생할 수 있음.
            Order order = new Order(2);
            Delivery delivery = new Delivery("서울시 강남구", "READY");

            order.setDelivery(delivery);
            // delivery도 cascade로 저장됨
            em.persist(order);

            tx.commit();

            Order foundOrder = em.find(Order.class, order.getId());

            System.out.println(foundOrder);

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}