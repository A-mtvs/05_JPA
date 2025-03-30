package com.ohgiraffers.event.chap01.section01.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/*
 * 📌 Quiz: 퀴즈 엔티티
 * - {DB}의 quizzes 테이블 매핑.
 * - Grade와의 관계를 위해 courseId 포함.
 */
@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "course_id", nullable = false)
    private int courseId;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public int getCourseId() { return courseId; }
}