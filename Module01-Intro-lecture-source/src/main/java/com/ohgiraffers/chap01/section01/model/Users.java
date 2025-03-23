package com.ohgiraffers.chap01.section01.model;

import java.time.LocalDate;

/*
 * 객체지향(OOP)과 RDB의 패러다임 차이: 객체 참조 vs 외래 키
 *
 * 이 `User` 클래스는 객체지향 프로그래밍의 관점에서 하나의 사용자(User)를 나타낸다.
 * 객체지향에서는 객체 간의 관계를 객체 참조로 표현하는 것이 일반적이다.
 * 예를 들어, `User` 객체가 특정 역할(Role)을 가진다고 할 때, 객체지향에서는 `Role role`과 같은 필드를 추가해
 * `User` 객체가 `Role` 객체를 직접 참조하도록 설계한다.
 *
 * 반면, 관계형 데이터베이스(RDB)에서는 관계를 외래 키(Foreign Key)로 표현한다.
 * `Users` 테이블에서 `role_id`라는 컬럼을 통해 `Roles` 테이블의 특정 레코드를 참조하는 방식이다.
 * 즉, RDB에서는 객체 참조 대신 단순히 `role_id`라는 정수 값으로 관계를 나타낸다.
 *
 * 따라서, 객체지향에서는 `User` 클래스에 `Role role` 필드를 두는 것이 자연스럽지만,
 * 데이터베이스에서는 `Users` 테이블에 `role_id`라는 외래 키 컬럼을 두는 것이 일반적이다.
 * 이 차이는 객체지향과 RDB의 패러다임 차이를 보여준다.
 *
 * 이러한 차이로 인해 설계와 구현에서 혼란이 발생할 수 있다.
 * - 객체지향 관점: `User` 객체는 `Role` 객체를 직접 참조하므로, `user.getRole().getRoleName()`과 같은 방식으로 역할 이름을 쉽게 가져올 수 있다.
 * - RDB 관점: `Users` 테이블의 `role_id` 값을 기반으로 `Roles` 테이블과 조인(join) 쿼리를 작성해야 역할 이름을 조회할 수 있다.
 * 이로 인해, 객체지향에서는 객체 간의 관계를 직관적으로 다룰 수 있지만, RDB에서는 추가적인 쿼리 작업이 필요하게 된다.
 *
 * 또한, `User` 클래스에서 `roleId` 필드를 단순히 정수로 두는 것은 객체지향적으로는 부자연스러운데
 * `roleId`는 `Role` 객체를 나타내는 참조가 아니라 단순한 외래 키 값에 불과하기 때문에,
 * 객체지향 설계의 캡슐화와 객체 간 협력 원칙에 어긋날 수 있다.
 *
 * 이 차이는 특정 이벤트의 주체와 동작을 이해하는 데 혼란을 초래할 수 있다.
 * EX) 사용자 역할 변경:
 *      - 객체지향에서는 `user.setRole(newRole)`로 새로운 `Role` 객체를 설정
 *      - RDB에서는 `UPDATE Users SET role_id = ?`와 같은 쿼리로 처리.
 * EX) 사용자 역할 조회:
 *      - 객체지향에서는 `user.getRole()`로 바로 접근 가능
 *      - RDB에서는 `SELECT * FROM Roles WHERE role_id = ?` 쿼리가 필요.
 * EX) 역할별 사용자 목록 조회:
 *      - 객체지향에서는 `Role` 객체를 통해 `users` 리스트를 탐색
 *      - RDB에서는 `SELECT * FROM Users WHERE role_id = ?`로 처리.
 */
public class Users {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private int roleId;
    private LocalDate createdAt;

    public Users() {
    }

    public Users(int userId, String username, String email, String passwordHash, int roleId, LocalDate createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.createdAt = createdAt;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", roleId=" + roleId +
                ", createdAt=" + createdAt +
                '}';
    }
}
