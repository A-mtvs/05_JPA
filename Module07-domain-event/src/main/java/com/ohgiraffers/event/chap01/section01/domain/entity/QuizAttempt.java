package com.ohgiraffers.event.chap01.section01.domain.entity;

import jakarta.persistence.*;


/*
 * 📌 QuizAttempt: 퀴즈 시도 엔티티
 * - {DB}의 quiz_attempts 테이블 매핑.
 * - section01 전용으로 최소 필드만 정의.
 */
@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private int attemptId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "quiz_id", nullable = false)
    private int quizId;

    @Column(name = "score")
    private double score;

    public QuizAttempt() {}

    public QuizAttempt(int userId, int quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }

    public void setScore(double score) { this.score = score; }
    public double getScore() { return score; }
    public int getAttemptId() { return attemptId; }
    public int getUserId() { return userId; }
    public int getQuizId() { return quizId; }

    @Override
    public String toString() {
        return "QuizAttempt{" +
                "attemptId=" + attemptId +
                ", userId=" + userId +
                ", quizId=" + quizId +
                ", score=" + score +
                '}';
    }
}