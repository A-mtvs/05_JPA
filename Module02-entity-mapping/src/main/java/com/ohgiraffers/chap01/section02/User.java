package com.ohgiraffers.chap01.section02;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * 📌 다양한 필드 매핑 문법 집중 학습
 * 이번 섹션에서는 실무에서 자주 사용되는 필드 타입과 제약조건 매핑 문법을 학습.
 *
 * ✅ 학습 주제
 * - @Column 속성의 다양한 옵션 (nullable, unique, length, precision 등)
 * - 날짜/시간 타입 매핑 (LocalDate, LocalDateTime)
 * - Enum 타입 매핑 (EnumType.STRING vs ORDINAL 비교)
 */
@Entity
@Table(name = "users")
public class User {
    /*
     * 📌 @Id + @GeneratedValue
     *
     * - @Id는 엔티티의 기본 키(PK)를 의미합니다.
     * - @GeneratedValue는 자동 생성 전략을 지정합니다.
     *   (IDENTITY는 MySQL 등에서 사용하는 auto_increment 방식)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /*
     * 📌 @Column의 속성들
     *
     * ▶ nullable = false → NOT NULL 제약조건 설정
     * ▶ unique = true → 유일성 보장 (UNIQUE 제약조건 생성)
     * ▶ length = 100 → VARCHAR(100)
     *
     * 💡 문자 타입(String)에만 length 속성이 적용됨
     */
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    /*
     * 📌 비밀번호는 일반 텍스트가 아니라 해시된 형태로 저장되어야 합니다.
     * ▶ 보안상 길이가 길 수 있으므로 VARCHAR(255)로 설정
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    /*
     * 📌 날짜 및 시간 타입 매핑
     *
     * ▶ LocalDate: 날짜만 저장 (yyyy-MM-dd)
     * ▶ LocalDateTime: 날짜 + 시간 저장 (yyyy-MM-dd HH:mm:ss)
     *
     * 🎯 JPA는 Hibernate 5 이상에서 java.time 패키지를 자동 지원함
     */
    @Column(name = "birth_date")
    private LocalDate birthDate; // 생년월일

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 계정 생성일시

    /*
     * 📌 @Enumerated - Enum 타입 필드 매핑
     *
     * ▶ 자바의 Enum을 테이블에 저장하는 방법
     *   - ORDINAL: Enum 순서(int) 저장 → ❌ 위험 (순서 바뀌면 망함)
     *   - STRING: Enum 이름 그대로 저장 → ✅ 추천 방식
     *
     * ▶ 실무에서는 항상 EnumType.STRING을 사용해야 유지보수가 안전합니다.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_id", nullable = false)
    private Role role;

    // ✅ 기본 생성자 (JPA가 내부적으로 리플렉션으로 객체를 생성할 수 있게 해줍니다)
    protected User() {}

    public User(String username, String email, String password, LocalDate birthDate, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.createdAt = LocalDateTime.now();
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", role=" + role +
                '}';
    }
}
