package com.ohgiraffers.chap01.section01.model;


/*
 * ORM의 RDB의 차이 1탄 : 네이밍
 * 이 클래스는 객체지향 프로그래밍의 관점에서 룰(Role)를 나타낸다.
 * 객체지향에서는 일반적으로 단수형을 사용하여 개별 객체를 표현한다.
 *
 * 반면, 데이터베이스에서는 테이블 이름을 복수형(Roles)으로 사용하는 것이 일반적이다.
 * 이는 테이블이 여러 개의 레코드를 포함하고 있기 때문이다.
 *
 * 따라서, 객체지향에서는 'Role'라는 클래스 이름을 사용하고,
 * 데이터베이스에서는 'Roles'라는 테이블 이름을 사용하는 것이
 * 패러다임 차이를 나타낸다.
 *
 * 이러한 차이는 ORM(Object-Relational Mapping)에서 중요한 역할을 하며,
 * JPA(Java Persistence API)와 같은 프레임워크를 사용할 때
 * 객체와 데이터베이스 간의 매핑을 정의하는 데 영향을 미친다.
 *
 * Role 클래스는 권한 정보를 관리하는 클래스로 인식을 하게된다.
 * 그러나 Roles 클래스는 권한들을 관리한다고 생각하며,
 * 이는 특정 "이벤트"의 주체가 어디인지 파악하는데 혼란이 생기게된다.
 *
 * EX) 신규 사용자에게 권한 부여
 * EX) 사용자 권한 변경
 * EX) 특정 권한 목록 조회
 */
public class Roles {
    private int roleId;
    private String roleName;

    public Roles() {
    }

    public Roles(int roleId, String roleName) {
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
