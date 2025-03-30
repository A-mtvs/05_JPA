package com.ohgiraffers.valueobject.chap01.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;


/*
 * Application 클래스에서는 호텔 예약 시스템의 기본적인 흐름을 보여주며,
 * 이 과정에서 값 객체(VO)가 데이터와 비즈니스 로직을 캡슐화하고
 * 도메인 모델을 더욱 풍부하고 견고하게 만드는 데 어떻게 기여하는지 확인할 수 있습니다.
 * JPA에서는 @Embeddable과 @Embedded를 통해 이러한 값 객체를 효과적으로 관리합니다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {
            StayPeriod period1 = new StayPeriod(
                    LocalDate.of(2025, 8, 1),
                    LocalDate.of(2025, 8, 3)
            );

            GuestCount guests1 = new GuestCount(2);
            Reservation reservation1 = new Reservation("김철수", "101호", guests1, period1, 50000);

            System.out.println("예상 총 가격: " + reservation1.calculateTotalPrice());

            // ❌ 체크인 날짜보다 이전인 체크아웃 날짜를 설정하려는 시도는 예외를 발생시킨다.
            // StayPeriod invalidPeriod = new StayPeriod(LocalDate.of(2025, 9, 5), LocalDate.of(2025, 9, 3));
            // Reservation reservation2 = new Reservation("박영희", "202호", new GuestCount(1), invalidPeriod, 70000);

            // ❌ 투숙객 수를 0 이하로 설정하려는 시도는 예외를 발생시킨다.
            // GuestCount invalidGuests = new GuestCount(0);
            // Reservation reservation3 = new Reservation("최자영", "303호", invalidGuests, period1, 60000);

            em.persist(reservation1);
            em.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 값입니다: " + e.getMessage());
            em.getTransaction().rollback();

        } catch (Exception e) {

            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
