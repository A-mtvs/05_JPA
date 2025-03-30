package com.ohgiraffers.jpql.chap01.section06;


import com.ohgiraffers.jpql.chap01.model.Course;
import com.ohgiraffers.jpql.chap01.section05.dto.CourseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/*
 * 📌 페이징과 정렬
 * - 페이징: setFirstResult, setMaxResults로 결과 범위 지정.
 * - 정렬: ORDER BY로 속성 기준 정렬.
 * 실생활 비유: 도서관에서 "가격순으로 정렬된 책 5권만 보여줘".
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();


        String jpql = "SELECT c FROM Course c";
        TypedQuery<Course> query = em.createQuery(jpql, Course.class);
        // 조회의 시작 부분
        query.setFirstResult(0);
        // 조회할 결과의 개수
        query.setMaxResults(10);

        List<Course> result = query.getResultList();
        for (Course course : result) {
            System.out.println(course.getCourseId() + " - " + course.getTitle());
        }

    }
}
