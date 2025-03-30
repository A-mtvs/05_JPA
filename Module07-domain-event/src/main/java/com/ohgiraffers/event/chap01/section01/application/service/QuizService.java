package com.ohgiraffers.event.chap01.section01.application.service;


import com.ohgiraffers.event.chap01.section01.domain.entity.Grade;
import com.ohgiraffers.event.chap01.section01.domain.entity.QuizAttempt;
import jakarta.persistence.EntityManager;

/*
 * 📌 QuizService: 퀴즈 관련 비즈니스 로직 처리
 * - 역할: 퀴즈 시도 생성, 채점, 성적 갱신 (section01에서는 이벤트 없이 직접 처리).
 * - 문제점: Grade 엔티티를 직접 수정하며 강한 결합 발생.
 */
public class QuizService {
    private final EntityManager em;

    public QuizService(EntityManager em) {
        this.em = em;
    }
    // 퀴즈 시도 생성
    public QuizAttempt createQuizAttempt(int userId, int quizId) {
        QuizAttempt attempt = new QuizAttempt(userId, quizId);
        em.persist(attempt);
        return attempt;
    }

    // 잘못된 방식: 퀴즈 채점 후 성적을 직접 갱신
    public void gradeQuiz(int attemptId, double score) {
        QuizAttempt attempt = em.find(QuizAttempt.class, attemptId);
        attempt.setScore(score);


        // 강한 결합: Grade 도메인을 직접 수정
        // 회원 id와 강의 id를 통해 점수 생성.
        Grade grade = em.createQuery(
                        "SELECT g FROM Grade g WHERE g.userId = :userId AND g.courseId = (SELECT q.courseId FROM Quiz q WHERE q.quizId = :quizId)",
                        Grade.class
                )
                .setParameter("userId", attempt.getUserId())
                .setParameter("quizId", attempt.getQuizId())
                .getSingleResult();


        grade.setFinalScore(score);
        grade.setStatus(score >= 60 ? "passed" : "failed");

        /* 주석: 문제점
         * - QuizService가 Grade 엔티티에 직접 접근, 도메인 간 경계 모호.
         * - 메모리: QuizAttempt와 Grade는 힙에 저장, 영속성 컨텍스트에서 관리되지만 결합도 높음.
         */
    }
}
