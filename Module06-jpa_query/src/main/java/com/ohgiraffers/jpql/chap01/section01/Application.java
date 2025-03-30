package com.ohgiraffers.jpql.chap01.section01;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

/*
 * 📌 JPQL이 없다면 발생하는 문제
 * 제공된 테이블(예: courses)을 기반으로 데이터를 조회하려면 Native SQL에 의존해야 한다.
 * 이는 객체 지향적이지 않고, 테이블명/컬럼명에 직접 의존하므로 유지보수가 어렵다.
 * 예: "가격이 200원 이상인 강좌"를 조회하려면 SQL을 작성해야 하며, 결과가 Object[]로 반환되어 타입 안전성이 떨어진다.
 * 실생활 비유: 도서관에서 책을 찾을 때 책 제목(속성)이 아닌 책장 번호(컬럼명)로 찾는 것과 비슷하다.
 */
/* SQL에 의존한 방식 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        // Native SQL 사용
        String sql = "SELECT * FROM courses WHERE price >= 300";
        // 객체지향적이지 않고 테이블명/컬럼명에 직접적인 의존으로 테이블 구조 변경시 코드 수정 발생
        List<Object[]> result = em.createNativeQuery(sql).getResultList();
        System.out.println(result.size());


        // 결과 처리: 타입 안전성 없음, 인덱스로 접근
        for (Object[] row : result) {
            // 인덱스 참조시 정확하다는 보장이 없음.
            System.out.println("Course ID: " + row[0] + ", Title: " + row[1]);
        }

        em.close();
        emf.close();
    }
}
