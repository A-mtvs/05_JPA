package com.ohgiraffers.jpql.mission.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "mission_courses")
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "instructor_id", nullable = false)
    private int instructorId;

    @Column(name = "price", columnDefinition = "DECIMAL(10,2) DEFAULT 0 CHECK (price >= 0)")
    private BigDecimal price;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Course() {
    }

    // Getters, Setters
    public int getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public BigDecimal getPrice() { return price; }
    @Override
    public String toString() { return "Course{title='" + title + "', price=" + price + "}"; }
}