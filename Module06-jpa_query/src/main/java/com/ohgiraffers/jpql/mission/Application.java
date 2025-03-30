package com.ohgiraffers.jpql.mission;

import com.ohgiraffers.jpql.mission.model.Course;
import com.ohgiraffers.jpql.mission.model.Lesson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        // 미션 1: 수강 신청한 강좌 목록 조회
        String enrollmentJpql = "SELECT c FROM mission_courses c " +
                "JOIN mission_enrollment e ON c.courseId = e.courseId " +
                "WHERE e.userId = :userId AND e.status = 'active'";

        TypedQuery<Course> enrollmentQuery = em.createQuery(enrollmentJpql, Course.class);
        enrollmentQuery.setParameter("userId", 8994); // 예: 학생 ID 1
        List<Course> enrolledCourses = enrollmentQuery.getResultList();
        System.out.println("=== 수강 신청한 강좌 목록 ===");
        enrolledCourses.forEach(course -> System.out.println("Enrolled Course: " + course.getTitle()));


        System.out.println("=========== 미션 2 =============");
        // 미션 2: 강사의 강의 목록 조회
        String instructorJpql = "SELECT l FROM mission_lessons l " +
                "JOIN mission_courses c ON l.courseId = c.courseId " +
                "WHERE c.instructorId = :instructorId " +
                "ORDER BY l.title";
        TypedQuery<Lesson> lessonQuery = em.createQuery(instructorJpql, Lesson.class);
        lessonQuery.setParameter("instructorId", 2); // 예: 강사 ID 2
        List<Lesson> lessons = lessonQuery.getResultList();
        System.out.println("=== 강사의 강의 목록 ===");
        lessons.forEach(lesson -> System.out.println("Lesson: " + lesson.getTitle()));

        /* 주석: 메모리 동작
         * - Course, Lesson 객체는 힙의 영속성 컨텍스트에 저장.
         * - JOIN 연산은 DB에서 실행 후 결과만 힙에 로드.
         * - 설계 이유: 객체 중심으로 관계를 매핑해 도메인 이벤트 흐름(수강 신청 → 조회)을 자연스럽게 구현.
         */

        em.close();
    }
}
