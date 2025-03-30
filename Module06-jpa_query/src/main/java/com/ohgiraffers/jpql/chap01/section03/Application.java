package com.ohgiraffers.jpql.chap01.section03;


import com.ohgiraffers.jpql.chap01.model.Course;
import com.ohgiraffers.jpql.chap01.model.Lesson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/*
 * 📌 TypedQuery
 * - 타입 안전성: 결과를 제네릭 타입으로 지정하여 컴파일 시점에 타입 오류를 감지할 수 있다.
 * - 객체 중심적 접근: 쿼리 결과를 직접 지정한 엔티티 클래스(예: Course)로 매핑하여,
 *   불필요한 형변환 없이 바로 사용할 수 있다.
 * - 가독성 향상: 코드의 가독성이 높아지고, 의도를 명확히 전달할 수 있다.
 *
 * 실생활 비유: 도서관에서 원하는 책의 정보를 요청할 때,
 *   "책 객체"를 직접 요청하여 책의 세부 정보를 즉시 받을 수 있는 것과 같다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        // JPQL을 이용하는 방식의 문제점.
        String jpql = "SELECT c FROM Course c WHERE c.price >= 300";
//        List<Lesson> courses = em.createQuery(jpql, Lesson.class).getResultList();
        // 런타임 시점에 오류가 발생
//        courses.stream().forEach(System.out::println);

        // Type Query
        String typedJpql = "SELECT c FROM Course c WHERE c.price >= 300";
        // 컴파일 시점에 오류가 발생됨
//        TypedQuery<Lesson> courses2 = em.createQuery(typedJpql, Lesson.class).getResultList();

        // 문제 없이 동작됨
        // 컴파일 시점에 타입의 안정성 확인가능.
        TypedQuery<Course> courses = em.createQuery(jpql, Course.class);
        courses.getResultList().forEach(course -> System.out.println(course.getCourseId() + " - " + course.getTitle()));

        /* 주석: 메모리 동작
         * - TypedQuery 객체는 힙에 생성, 결과는 영속성 컨텍스트에 캐싱.
         * - 성능: 컴파일 시점 타입 체크로 런타임 오류 감소.
         */
    }
}
