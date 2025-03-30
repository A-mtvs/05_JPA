package com.ohgiraffers.event.chap01.section01.domain.entity;


import jakarta.persistence.*;

/*
 * 📌 Grade: 성적 엔티티
 * - {DB}의 grades 테이블 매핑.
 * - section01 전용으로 최소 필드만 정의.
 */
@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "course_id", nullable = false)
    private int courseId;

    @Column(name = "final_score")
    private Double finalScore;

    @Column(name = "status")
    private String status = "in_progress";

    public void setFinalScore(Double finalScore) { this.finalScore = finalScore; }
    public void setStatus(String status) { this.status = status; }
    public int getUserId() { return userId; }
    public int getCourseId() { return courseId; }
}