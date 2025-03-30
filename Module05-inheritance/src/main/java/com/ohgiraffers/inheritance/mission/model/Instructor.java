package com.ohgiraffers.inheritance.mission.model;

import jakarta.persistence.Entity;

@Entity
public class Instructor extends User {

    private String majorSubject;    // 전공 과목

    protected Instructor() {}

    public Instructor(String username, String email, String passwordHash, String majorSubject) {
        super(username, email, passwordHash, 2L);
        this.majorSubject = majorSubject;
    }

    public String getMajorSubject() {
        return majorSubject;
    }

    public void setMajorSubject(String majorSubject) {
        this.majorSubject = majorSubject;
    }

    @Override
    public String toString() {
        return "강사 정보{" +
                "전공 과목 ='" + majorSubject + '\'' +
                super.toString();
    }
}