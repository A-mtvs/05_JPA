package com.ohgiraffers.chap02.model;

import jakarta.persistence.*;
// * mata-inf 클래스 등록*

/*
 * 객체지향(OOP)과 RDB의 패러다임 차이: 네이밍 문제와 JPA의 해결
 *
 * 이 `Role` 클래스는 객체지향 프로그래밍의 관점에서 하나의 권한(Role)을 나타낸다.
 * 객체지향에서는 일반적으로 단수형을 사용하여 개별 객체를 표현한다.
 * 따라서 클래스 이름으로 `Role`을 사용하는 것이 자연스럽다.
 *
 * 📌 JPA가 이 문제를 해결하는 방법:
 * JPA(Java Persistence API)는 객체지향과 RDB 간의 패러다임 차이를 해결하기 위해 매핑 메커니즘을 제공한다.
 * - `@Entity` 어노테이션을 사용해 `Role` 클래스를 JPA 엔티티로 정의한다.
 * - `@Table(name = "roles")` 어노테이션을 사용해 객체지향의 `Role` 클래스와 RDB의 `Roles` 테이블을 명시적으로 매핑한다.
 *   이를 통해 이름 관례의 차이(단수형 vs 복수형)를 조정한다.
 * - `@Column(name = "role_id")`와 `@Column(name = "role_name")`을 사용해 필드(`roleId`, `roleName`)와 테이블 컬럼(`role_id`, `role_name`) 간의 이름 차이를 매핑한다.
 * - `@Id`와 `@GeneratedValue`를 사용해 `roleId`를 기본 키로 설정하고, 자동 증가 값을 관리하여 RDB의 기본 키 제약 조건을 객체지향적으로 처리한다.
 *
 * JPA를 통해 객체지향의 `Role` 클래스는 RDB의 `Roles` 테이블과 자연스럽게 연결되며,
 * 네이밍 차이로 인한 혼란 없이 객체와 테이블 간의 매핑을 일관성 있게 유지할 수 있다.
 */
@Entity
@Table(name ="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "role_name")
    private String roleName;

    /*
     * 📌 기본 생성자가 필요한 이유:
     * JPA는 엔티티 객체를 생성하고 관리하는 과정에서 "기본 생성자"를 반드시 요구한다.
     * - JPA의 동작 방식: JPA는 데이터베이스에서 조회한 데이터를 객체로 매핑할 때, 먼저 기본 생성자를 호출해 객체를 생성한 후,
     *   리플렉션(Reflection)을 사용해 필드(`roleId`, `roleName`)에 값을 주입한다.
     * - 기본 생성자 없음 시 문제: 만약 기본 생성자가 없다면, JPA는 객체를 생성할 수 없어 `InstantiationException`이 발생한다.
     * - 예: `em.find(Role.class, 1)`로 `Role` 객체를 조회할 때, JPA는 `new Role()`을 호출해 빈 객체를 생성한 후,
     *   데이터베이스에서 가져온 `role_id`와 `role_name` 값을 필드에 설정한다.
     * - 추가 생성자: `public Role(int roleId, String roleName)`과 같은 생성자는 개발자가 객체를 생성할 때 유용하지만,
     *   JPA는 이를 사용하지 않으므로, 반드시 인자 없는 기본 생성자(`public Role()`)가 필요하다.
     * - 접근 제어자: 기본 생성자는 `public` 또는 `protected`로 설정해야 JPA가 접근할 수 있다.
    * */
    public Role() {

    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
