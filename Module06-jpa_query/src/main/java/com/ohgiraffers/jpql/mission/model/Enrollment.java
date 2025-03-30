package com.ohgiraffers.jpql.mission.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/* Enrollment 엔티티 */
@Entity(name = "mission_enrollment")
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private int enrollmentId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "course_id", nullable = false)
    private int courseId;

    @Column(name = "enrolled_at", insertable = false, updatable = false)
    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('active', 'completed', 'canceled') DEFAULT 'active'")
    private EnrollmentStatus status;

    public Enrollment() {
    }

    // Getters, Setters
    public int getUserId() { return userId; }
    public int getCourseId() { return courseId; }
}

