package com.ohgiraffers.event.chap01.section01;


import com.ohgiraffers.event.chap01.section01.application.service.QuizService;
import com.ohgiraffers.event.chap01.section01.domain.entity.QuizAttempt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/*
 * 📌 도메인 간 결합이 높으면 생기는 문제
 * - 강한 결합: 퀴즈 채점 후 성적을 직접 갱신하면 Quiz와 Grade 도메인 간 의존성 증가.
 * - 유지보수 어려움: 상태 변경 로직이 서비스에 흩어져 수정 시 연쇄 영향 발생.
 * - 실생활 비유: 강의실에서 퀴즈를 채점한 후 교수가 직접 성적표를 수동으로 업데이트해야 하는 번거로움.
 */
public class Application01 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();
        QuizService quizService = new QuizService(em);

        em.getTransaction().begin();

        // 퀴즈 시도 생성 및 채점 (잘못된 방식)
        QuizAttempt attempt = quizService.createQuizAttempt(999, 999);
        quizService.gradeQuiz(attempt.getAttemptId(), 85.0);
        System.out.println("퀴즈 채점 완료: " + attempt.getScore());

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}