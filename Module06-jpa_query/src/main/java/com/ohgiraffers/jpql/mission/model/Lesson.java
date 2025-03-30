package com.ohgiraffers.jpql.mission.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/* Lesson 엔티티 */
@Entity(name = "mission_lessons")
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int lessonId;

    @Column(name = "course_id", nullable = false)
    private int courseId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "video_url", length = 500)
    private String videoUrl;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Lesson() {
    }

    // Getters, Setters
    public String getTitle() { return title; }

    @Override
    public String toString() { return "Lesson{title='" + title + "'}"; }
}

