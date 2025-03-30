package com.ohgiraffers.chap01.section04;

import com.ohgiraffers.chap01.section04.model.Customer;
import com.ohgiraffers.chap01.section04.model.Delivery;
import com.ohgiraffers.chap01.section04.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;


/*
 * 📌 이 파일은 section01 ~ 03까지 배운 연관관계 매핑 개념을 종합하여 테스트하는 통합 실습.
 *
 * 🎯 아래의 실습 내용을 포함한다:
 *  1. 무한 루프 방지 (toString, @JsonIgnore 등)
 *  2. N+1 문제 발생 및 지연 로딩 확인
 *  3. 연관관계 주인 변경 시 insert 쿼리 동작 비교
 */
public class Application {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // 고객 생성
            Customer customer = new Customer("심화테스트 고객");

            // 주문 1개 생성 및 설정
            Order order = new Order();
            customer.addOrder(order); // 연관관계 양방향 설정 (Customer ↔ Order)

            // 배송 정보 설정
            Delivery delivery = new Delivery("서울시 심화구", "READY");
            order.setDelivery(delivery); // 양방향 연결

            // 고객 저장 → cascade로 주문과 배송도 함께 저장
            em.persist(customer);

            em.getTransaction().commit();
            em.clear(); // 영속성 컨텍스트 초기화 (캐시 클리어)

            System.out.println("\n===== 📌 N+1 문제 실습: 고객 목록에서 주문 조회 =====");
            em.getTransaction().begin();

            List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();

            for (Customer c : customers) {
                // 🎯 이 시점에 주문이 로딩되는가? Lazy 로딩 확인
                System.out.println("고객 이름: " + c.getName());

                // 🎯 이 시점에 배송이 로딩되는가? Eager 로딩 확인
                // 해당 시점에 n+1 문제 발생. Order만 찾았으나 deliveries까지 조회됨.
                System.out.println("주문 수: " + c.getOrders().size());
            }

            em.getTransaction().commit();

        } finally {
            em.close();
            emf.close();
        }
    }
}
