package com.ohgiraffers.inheritance.mission.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // TABLE_PER_CLASS는 AUTO 또는 TABLE 사용
    @Column(name = "user_id")
    private Long userId;

    private String username;
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    protected User() {}

    public User(String username, String email, String passwordHash, Long roleId) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    public void login() {
        System.out.println(username + " 님이 로그인하셨습니다.");
    }

    public void updateProfile(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Long getRoleId() {
        return roleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "공통 정보{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", roleId=" + roleId +
                ", createdAt=" + createdAt +
                '}';
    }
}
