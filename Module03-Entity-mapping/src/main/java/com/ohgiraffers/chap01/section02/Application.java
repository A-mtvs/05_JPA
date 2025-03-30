package com.ohgiraffers.chap01.section02;


import com.ohgiraffers.chap01.section02.model.Customer;
import com.ohgiraffers.chap01.section02.model.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


/*
 * 🎯 양방향 연관관계 매핑
 * - 고객은 여러 주문을 가질 수 있고, 각 주문은 하나의 고객에게 귀속된다.
 * - 이 경우, Order 클래스에서 Customer를 참조하고,
 *   Customer 클래스에서 Order 목록을 참조하여 양방향 관계를 형성한다.
 *
 * */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Customer customer = new Customer("김영희");

            Order order = new Order();
            customer.addOrder(order);

            /*
             * Cascade
             * JPA에서 엔티티 간의 연관관계에 대해 특정 작업(예: 저장, 삭제 등)을 전파하는 방식이다.
             * 즉, 부모 엔티티에 대해 수행된 작업이 자식 엔티티에도 자동으로 적용될 수 있도록하는 설정이다.
             * 이는 코드의 간결성을 유지하고, 연관된 엔티티 간의 작업 일관성을 보장하기 위한 목적으로 사용된다.
             *
             * - customer.addOrder(order) 메서드를 호출하여
             *   Customer 객체에 Order 객체를 추가함으로써
             *   두 객체 간의 연관관계를 설정한다.
             *
             * - 이때, Order 객체는 아직 영속 상태가 아니므로,
             *   고객의 주문 목록에 추가되더라도
             *   JPA의 영속성 컨텍스트에는 반영되지 않는다.
             *
             * - em.persist(customer) 메서드를 호출하면,
             *   Customer 객체가 영속 상태로 전이되고,
             *   그에 따라 연관된 Order 객체도 함께 영속 상태로 전이된다.
             *
             * - 이 과정에서 JPA는 Customer와 연관된 모든 Order 객체를
             *   자동으로 관리하게 되며,
             *   결과적으로 Order 객체의 상태가 영속성 컨텍스트에 반영된다.
             *
             */
            em.persist(customer);

            Customer foundCustomer = em.find(Customer.class, customer.getId());

            System.out.println("주문 목록 : ");
            foundCustomer.getOrders().forEach(System.out::println);


            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}