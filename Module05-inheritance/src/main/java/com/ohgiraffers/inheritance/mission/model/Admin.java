package com.ohgiraffers.inheritance.mission.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin extends User {

    private String position;   // 직책
    private String department; // 부서

    protected Admin() {}

    public Admin(String username, String email, String passwordHash, String position, String department) {
        super(username, email, passwordHash,1L);
        this.position = position;
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }


    @Override
    public String toString() {
        return "관리자 정보 " +
                "직책='" + position + '\'' +
                ", 부서 ='" + department + '\'' +
                super.toString();
    }
}