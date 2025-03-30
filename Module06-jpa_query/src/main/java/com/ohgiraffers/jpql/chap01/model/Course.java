package com.ohgiraffers.jpql.chap01.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="course_id")
    private int courseId;
    private String title;
    private String description;

    @Column(name = "instructor_id")
    private int instructorId;
    private double price;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {
    }

    public Course(String title, String description, int instructorId, double price) {
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "강좌 정보{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructorId=" + instructorId +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}
