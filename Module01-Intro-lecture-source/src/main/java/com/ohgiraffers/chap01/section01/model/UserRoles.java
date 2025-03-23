package com.ohgiraffers.chap01.section01.model;

import java.time.LocalDate;
/*
 * 객체지향(OOP)과 RDB의 패러다임 차이: DTO 사용으로 인한 문제
 *
 * 이 `UserAndRole` 클래스는 객체지향 프로그래밍에서 RDB의 데이터를 표현하기 위해 설계된 DTO(Data Transfer Object)이다.
 * 객체지향에서는 `User`와 `Role`을 각각 별도의 객체로 설계하고, `User`가 `Role` 객체를 참조(예: `Role role`)하는 방식으로 관계를 표현하는 것이 자연스럽다.
 * 이를 통해 `User` 객체는 `Role` 객체와의 관계를 캡슐화하고, 객체 간 협력을 통해 동작을 처리할 수 있다.
 *
 * 반면, 관계형 데이터베이스(RDB)에서는 `Users`와 `Roles` 테이블을 조인(join)하여 데이터를 조회하는 것이 일반적인데
 * 예를 들어, 사용자이름, 이메일, 권한 이름을 조회하는 과정에서 조회된 데이터를 객체지향적으로 다루기 위해
 * `UserAndRole`과 같은 DTO 클래스를 생성하게 된다.
 *
 * 따라서, 객체지향에서는 `User`와 `Role`을 별도의 객체로 나누어 설계하지만,
 * RDB에서는 조인된 데이터를 하나의 평면적인 구조로 반환하므로, 이를 담기 위해 `UserAndRole`과 같은 DTO를 사용하게 된다.
 * 이 차이는 객체지향과 RDB의 패러다임 차이를 보여준다.
 *
 * 이러한 차이로 인해 설계와 구현에서 혼란이 발생할 수 있다.
 * - 객체지향 관점: `User`와 `Role` 객체를 분리하여 각각의 책임과 관계를 명확히 정의할 수 있다.
 *      - 예) `user.getRole().getRoleName()`으로 역할 이름을 가져옴.
 * - RDB 관점: 조인 쿼리로 데이터를 평면화하여 반환하므로, `UserAndRole` DTO에 `roleName`을 직접 포함시켜야 한다. 이로 인해 객체지향의 캡슐화 원칙이 약화됨.
 * DTO를 사용하면 `User`와 `Role`의 관계가 단순한 데이터로 평면화되어, 객체지향적인 설계의 장점(예: 객체 간 협력, 캡슐화)이 손실된다.
 * 또한, DTO는 데이터 전달만을 목적으로 하므로, `User` 객체가 가질 수 있는 동작(메서드)이 제외되어 객체지향의 풍부한 표현력이 제한된다.
 *
 * 이 차이는 특정 이벤트의 주체와 동작을 이해하는 데 혼란을 초래할 수 있다.
 * EX) 사용자 역할 변경: 객체지향에서는 `user.setRole(newRole)`로 새로운 `Role` 객체를 설정하지만, DTO에서는 `roleName`을 직접 수정하거나 새로운 DTO를 생성해야 함.
 * EX) 역할별 사용자 목록 조회: 객체지향에서는 `Role` 객체를 통해 `users` 리스트를 탐색하지만, DTO에서는 조인 쿼리로 데이터를 다시 조회해야 함.
 */
public class UserRoles {
    private int userId;
    private String username;
    private String email;
    private String passwordHash;
    private String roleName;
    private LocalDate createdAt;


    public UserRoles(int userId, String username, String email, String passwordHash, String roleName, LocalDate createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roleName = roleName;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserAndRole{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", roleName='" + roleName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
