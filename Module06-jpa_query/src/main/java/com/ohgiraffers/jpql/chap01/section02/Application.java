package com.ohgiraffers.jpql.chap01.section02;


import com.ohgiraffers.jpql.chap01.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

/*
 * 📌 JPQL 기본 문법
 * - 객체 중심: 테이블(courses)이 아닌 엔티티(Course)와 속성명(title, price)으로 쿼리 작성.
 * - SQL과 유사: SELECT, FROM, WHERE 등 키워드 사용.
 * 실생활 비유: 도서관에서 "제목이 'Java'인 책"을 찾듯, 객체 속성으로 데이터를 조회.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        // JPQL을 이용하는 방식
        String jpql = "SELECT c FROM Course c WHERE c.price >= 300";
        List<Course> courses = em.createQuery(jpql, Course.class).getResultList();
        System.out.println("============   단일 테이블 조회 ================");
        courses.forEach(course -> System.out.println(course.getTitle() + " - " + course.getPrice()));


        System.out.println("============   다중 테이블 조회  ================");
        String joinQuery = "SELECT c FROM Course c join c.lessons l WHERE c.price >= 300";

        courses = em.createQuery(joinQuery, Course.class).getResultList();
        for (Course course : courses) {
            System.out.println(course.getTitle() + " : " + course.getCourseId());
            course.getLessons().forEach(System.out::println);
            System.out.println();
            System.out.println();
        }

        /* 주석: 메모리 동작
         * - JPQL은 엔티티 객체를 대상으로 실행, 결과는 힙의 영속성 컨텍스트에 저장.
         * - 설계 이유: 객체 중심으로 데이터를 다루므로 코드 가독성과 유지보수성 향상.
         */
        em.close();
    }
}
