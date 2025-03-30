package com.ohgiraffers.inheritance.mission.model;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Student extends User {

    private String studentId;           // 학번

    protected Student() {}

    public Student(String username, String email, String passwordHash, String studentId) {
        super(username, email, passwordHash, 3L);
        this.studentId = studentId;
    }


    @Override
    public String toString() {
        return "학생 젇보 = " +
                "studentId='" + studentId + '\'' +
                super.toString();
    }
}