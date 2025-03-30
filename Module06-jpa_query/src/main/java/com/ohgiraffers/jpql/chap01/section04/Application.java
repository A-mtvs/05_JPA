package com.ohgiraffers.jpql.chap01.section04;


import com.ohgiraffers.jpql.chap01.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/*
 * 📌 Query Parameter 바인딩
 * - 동적 값 처리: 쿼리에 변수(:param)를 사용해 안전하게 값 주입.
 * - 객체 중심: 속성명(price)에 직접 바인딩.
 * 실생활 비유: 도서관에서 "가격이 X원 이상인 책"을 찾을 때 X를 동적으로 지정.
 */
public class Application {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        // 하드코딩 방식
        String badJpql = "SELECT c FROM Course c WHERE c.title LIKE '%자바%'";
        TypedQuery<Course> courses = em.createQuery(badJpql, Course.class);
        courses.getResultList().forEach(course -> System.out.println(course.getTitle() + " - " + course.getCourseId()));


        // 만약 데이터 베이스 검색 요청을 해야한다면?
        // 하드코딩을 하는 방식으로 대응이 어렵다.

        // 파라미터 바인딩
        String jpql = "SELECT c FROM Course c WHERE c.title LIKE :title";
        TypedQuery<Course> findCourse = em.createQuery(jpql, Course.class);
        // 다음과 같이 파라미터를 바인딩하여 사용이 가능하다.
        findCourse.setParameter("title", "%데이터%");
        List<Course> courseList = findCourse.getResultList();

        courseList.forEach(course -> System.out.println(course.getCourseId() + " - " + course.getTitle()));


    }
}
