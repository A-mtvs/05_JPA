package com.ohgiraffers.jpql.chap01.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int lessonId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Lesson() {
    }

    public Lesson(Course course, String title, String content, String videoUrl) {
        this.lessonId = lessonId;
        this.course = course;
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.createdAt = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "강의 정보 = {" +
                "lessonId=" + lessonId +
                ", title='" + title + '\'' +
                ", course id ='" + course.getCourseId()  +
                '}';
    }
}
