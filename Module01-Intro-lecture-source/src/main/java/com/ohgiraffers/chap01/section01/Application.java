package com.ohgiraffers.chap01.section01;

import com.ohgiraffers.chap01.section01.model.Roles;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

/*
 * 객체지향(OOP)과 RDB의 패러다임 차이: 동일 데이터의 서로 다른 인스턴스와 무결성 문제
 *
 * `Application` 클래스는 객체지향 프로그래밍에서 RDB의 데이터를 조회하여 `Roles` 객체를 생성한다.
 * 객체지향에서는 동일한 데이터를 나타내는 객체가 동일한 인스턴스로 관리되기를 기대한다.
 * 예를 들어, 동일한 `roleId`를 가진 `Roles` 객체는 동일한 객체로 간주되어야 하며, `roles == roles2`가 `true`를 반환해야 한다.
 * 이는 객체 식별성(Identity)과 관련이 있다.
 *
 * 반면, 관계형 데이터베이스(RDB)에서는 동일한 데이터를 조회하더라도 매번 새로운 데이터 행(row)을 반환한다.
 * `getRoles` 메서드는 동일한 `roleId`로 쿼리를 실행할 때마다 새로운 `Roles` 객체를 생성하므로,
 * `roles`와 `roles2`는 서로 다른 인스턴스로 인식된다.
 *
 * 따라서, 객체지향에서는 동일한 데이터를 나타내는 객체를 단일 인스턴스로 관리하려는 경향이 있다.
 * RDB에서는 데이터를 행 단위로 조회하여 새로운 객체를 생성하는 방식이 일반적이다.
 * 이 차이는 객체지향과 RDB의 패러다임 차이를 보여준다.
 *
 * 📌 무결성(Integrity) 문제: 동일 데이터의 서로 다른 인스턴스로 인한 수정 반영 문제
 * RDB에서는 동일한 데이터를 여러 번 조회하더라도 동일한 데이터 행(row)을 반환하며, 데이터 무결성을 보장한다.
 * 예를 들어, `Roles` 테이블에서 `role_id=1`인 데이터를 조회하면 항상 동일한 데이터가 반환된다.
 * 그러나 `getRoles` 메서드는 동일한 `roleId`로 조회할 때마다 새로운 `Roles` 객체를 생성하므로,
 * 서로 다른 인스턴스(예: A 인스턴스와 B 인스턴스)가 만들어진다.
 * 이로 인해 무결성 문제가 발생한다:
 * - A 인스턴스를 조회하여 `roleName`을 "Admin"으로 수정하고 데이터베이스에 반영한 후,
 *   동일한 `roleId`로 B 인스턴스를 조회하면 B 인스턴스는 A의 수정 사항을 반영하지 않는다.
 * - 이후 B 인스턴스를 `roleName`을 "User"로 수정하고 저장하면, A 인스턴스의 수정 사항이 덮어씌워져 데이터 불일치가 발생한다.
 *
 * 📌 동시성 문제(Concurrency Issue)와의 관련성
 * 위 문제는 동시성 문제로 확장될 수 있다:
 * - 동일한 데이터를 서로 다른 인스턴스(A와 B)로 관리하는 상황은 여러 스레드나 프로세스가 동시에 동일한 데이터를 조회하고 수정할 때 동시성 문제를 유발한다.
 * - 예를 들어, 스레드 1이 A 인스턴스를 조회하여 `roleName`을 "Admin"으로 수정하는 동안,
 *   스레드 2가 B 인스턴스를 조회하여 `roleName`을 "User"로 수정하면, 두 스레드가 서로의 변경 사항을 덮어쓰게 된다.
 *   이는 전형적인 **"Lost Update"** 문제이다.
 * - 현재 코드에서는 단일 스레드 환경에서 동작하지만, 동일 데이터를 서로 다른 인스턴스로 관리하는 설계 자체가 동시성 환경에서 문제를 일으킬 가능성을 내포하고 있다.
 *
 * 📌 추가 문제: SQL 의존성과 유지보수성
 * `getRoles` 메서드 내에서 SQL 쿼리에 직접 의존하여 데이터를 조회한다.
 * 이는 객체지향 코드가 RDB의 스키마와 쿼리에 강하게 결합되어 요구사항 변경 시 SQL 수정이 필요하다.
 * 예를 들어, `Roles` 테이블에 새로운 컬럼이 추가되거나 컬럼 이름이 변경되면, `getRoles` 메서드의 쿼리와 객체 매핑 코드도 수정해야 한다.
 * 이는 유지보수성을 저하시키고 오류 가능성을 높인다.
 * 또한, 객체를 사용할 때 SQL 쿼리를 확인해야 객체에 어떤 값이 들어있는지 알 수 있다.
 *
 * 이러한 차이로 인해 설계와 구현에서 혼란이 발생할 수 있다:
 * - 객체지향 관점: 동일한 `roleId`를 가진 `Roles` 객체는 동일한 인스턴스로 간주되어야 하며, 비교 시 `==`로 동일성을 확인할 수 있어야 한다.
 * - RDB 관점: 동일한 데이터를 조회하더라도 매번 새로운 객체를 생성하므로, `roles == roles2`가 `false`로 평가된다.
 *   이로 인해 동일 데이터를 서로 다른 인스턴스로 관리하여 수정 시 데이터 불일치가 발생할 수 있다.
 * 이로 인해 동일한 데이터를 나타내는 객체가 서로 다른 인스턴스로 관리되어 문제가 발생할 수 있다.
 * 또한, 동시성 환경에서는 이러한 문제가 더 심화되어 데이터 무결성이 깨질 가능성이 높아진다.
 * 예를 들어, 동일한 권한 객체를 여러 번 조회할 때마다 새로운 인스턴스가 생성되면 메모리 사용량이 증가하고,
 * 객체 비교 시 예상치 못한 결과가 발생할 수 있다.
 *
 * 이 차이는 특정 이벤트의 주체와 동작을 이해하는 데 혼란을 초래할 수 있다:
 * EX) 동일 권한 비교: 객체지향에서는 `roles == roles2`로 동일성을 확인하려 하지만, RDB에서는 새로운 인스턴스가 생성되어 비교 실패.
 * EX) 권한 캐싱: 동일한 권한 객체를 캐싱하여 재사용하려 하지만, 매번 새로운 객체가 생성되어 캐싱 효과 상실.
 * EX) 권한 수정: A 인스턴스에서 권한 이름을 수정한 후, B 인스턴스에서 동일 데이터를 수정하면 A의 변경 사항이 손실된다.
 * EX) 동시 수정: 여러 스레드가 동일 데이터를 서로 다른 인스턴스로 조회하고 수정하면 "Lost Update" 문제로 데이터 무결성이 깨진다.
 */

public class Application {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/HELLOWORLDLABS_LMS");
        config.setUsername("gorilla");
        config.setPassword("gorilla");
        dataSource = new HikariDataSource(config);
    }


    public static void main(String[] args) throws SQLException {
        /* 동일성 보장 문제 */
        Roles roles = getRoles(1);
        Roles roles1 = getRoles(1);

        System.out.println(roles == roles1);

    }

    private static Roles getRoles(int roleId) {
        /* SQL에 의존하여 개발 */
        String sql = "SELECT role_id, role_name FROM roles WHERE role_id = ?";

        // try-with-resources로 자원 자동 관리
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            // 파라미터 설정으로 SQL 인젝션 방지
            pstmt.setInt(1, roleId);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                Roles role = new Roles();
                role.setRoleId(rset.getInt("role_id"));
                role.setRoleName(rset.getString("role_name"));
                return role;
            } else {
                // 데이터가 없을 경우 null 반환 (또는 예외 처리)
                return null;
            }
        } catch (SQLException e) {
            // 예외를 래핑하여 상위 호출자에게 전달
            throw new RuntimeException("Failed to retrieve role with role_id=" + roleId, e);
        }
    }

}
