package com.ohgiraffers.jpql.chap01.section05;

import com.ohgiraffers.jpql.chap01.section05.dto.CourseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

/*
 * 📌 DTO 결과 매핑
 * - NEW 키워드: 쿼리 결과를 DTO 객체로 직접 매핑.
 * - 객체 중심: 필요한 속성만 선택해 메모리 효율성 증가.
 * 실생활 비유: 도서관에서 책 전체가 아닌 제목과 가격만 요청.
 *
 * 표현식
 * SELECT [new 패기지명.DTO()] FROM 엔티티;
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        /*
        영속성 컨텍스트는 데이터베이스에서 조회된 개별 엔티티를 관리한다. 
        GROUP BY 절은 데이터베이스에서 데이터를 집계하는 역할을 하지만, 
        JPA는 그룹화되기 전의 개별 Course 엔티티와 Lesson 엔티티를 영속성 컨텍스트에 로드한다.
        */
        String jpql = " SELECT new com.ohgiraffers.jpql.chap01.section05.dto.CourseDTO(c.courseId, c.title, COUNT(l))" +
                  " FROM Course c JOIN c.lessons l  GROUP BY c.courseId, c.title" +
                " HAVING COUNT(l) > :cnt";

        TypedQuery<CourseDTO> query = em.createQuery(jpql, CourseDTO.class);
        query.setParameter("cnt", 5);

        List<CourseDTO> values = query.getResultList();
        values.forEach(System.out::println);
    }
}
