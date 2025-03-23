package com.ohgiraffers.chap02.section01;

import com.ohgiraffers.chap02.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/*
 * 객체지향(OOP)과 RDB의 패러다임 차이: 동일 데이터의 서로 다른 인스턴스 문제와 JPA의 해결
 *
 * 📌 JPA가 이 문제를 해결하는 방법
 * JPA(Java Persistence API)는 객체지향과 RDB 간의 패러다임 차이를 해결하기 위해 영속성 컨텍스트(Persistence Context)를 제공한다.
 * - 영속성 컨텍스트의 역할: `EntityManager`는 영속성 컨텍스트를 통해 엔티티를 관리한다.
 *   영속성 컨텍스트는 동일한 엔티티(예: 동일 `roleId`를 가진 `Role` 객체)를 단일 인스턴스로 관리하는 "1차 캐시" 역할을 한다.
 * - 동일성 보장: `em.getReference(Role.class, 1)`를 두 번 호출하여 `role`과 `role2`를 조회하면,
 *   영속성 컨텍스트가 동일한 `roleId`에 대해 동일한 인스턴스를 반환하므로 `role == role2`가 `true`로 평가된다.
 *   이는 영속성 컨텍스트가 동일 데이터를 단일 인스턴스로 관리하기 때문에 가능하다.
 *   "여기서 동일한 인스턴스를 구분하는 기준은 @Id 이다."
 *
 * - 무결성 유지: 영속성 컨텍스트는 엔티티의 상태 변화를 추적(Dirty Checking)하여,
 *   트랜잭션 커밋 시 변경 사항을 자동으로 데이터베이스에 반영한다.
 *   예를 들어, `role` 인스턴스를 수정하면 동일한 `roleId`를 가진 `role2`도 동일한 인스턴스를 참조하므로,
 *   수정 사항이 일관되게 반영되어 A와 B 인스턴스 간의 데이터 불일치 문제가 해결된다.
 *
 * - 동시성 문제 완화 : JPA는 낙관적 락(Optimistic Locking)이나 비관적 락(Pessimistic Locking)을 제공하여 동시성 문제를 관리할 수 있다.
 *   예를 들어, `@Version` 어노테이션을 사용해 낙관적 락을 적용하면, 여러 스레드가 동일 데이터를 수정할 때 충돌을 감지하고 예외를 발생시켜 "Lost Update" 문제를 방지할 수 있다.
 *      - 낙관적 락 : 동시 접근이 발생하지 않을 것이라고 가정하는 방식으로 다른 트랜잭션이 데이터를 수정할 가능성이 낮다고 판단함.
 *      - 비관적 락 : 동시 접근이 발생할 것이라고 가정하는 방식으로 다른 트랜잭션이 데이터를 수정할 가능성이 높다면, 해당 데이터를 잠궈서 다른 트랜잭션이 접근하지 못하도록함.
 * - SQL 의존성 감소: 이전 코드에서는 SQL 쿼리에 직접 의존하여 데이터를 조회했지만,
 *   JPA는 `em.getReference()`와 같은 메서드를 통해 객체지향적으로 데이터를 조회하므로,
 *   SQL 쿼리를 직접 작성할 필요가 없어 유지보수성이 향상된다.
 *
 * 📌 해결 결과
 * JPA를 사용하면 동일 데이터를 단일 인스턴스로 관리하여 객체지향의 객체 식별성 기대를 충족시킬 수 있다.
 * - `role == role2`가 `true`로 평가되어 동일 데이터가 동일한 인스턴스로 관리됨.
 * - A와 B 인스턴스 간의 데이터 불일치 문제가 해결되며, 무결성이 유지됨.
 * - 동시성 환경에서도 JPA의 락 메커니즘을 통해 데이터 무결성을 보장할 수 있음.
 * - SQL 쿼리에 대한 의존성이 줄어들어 객체지향적인 설계와 유지보수성이 향상됨.
 *
 * 이 차이는 특정 이벤트의 주체와 동작을 이해하는 데 혼란을 줄였던 문제를 해결한다:
 * EX) 동일 권한 비교: JPA를 사용하면 `role == role2`가 `true`로 평가되어 비교가 성공.
 * EX) 권한 캐싱: 영속성 컨텍스트가 1차 캐시 역할을 하여 동일 데이터를 재조회하지 않고 재사용 가능.
 * EX) 권한 수정: 동일 데이터를 단일 인스턴스로 관리하므로, A와 B 인스턴스 간의 수정 충돌이 발생하지 않음.
 * EX) 동시 수정: JPA의 낙관적 락을 사용하면 여러 스레드가 동일 데이터를 수정할 때 충돌을 감지하여 데이터 무결성 유지.
 */

public class Application {

    private static final EntityManagerFactory EMF;


    static {
        /*
         * 📌 EntityManagerFactory는 영속성 유닛(Persistence Unit)을 기반으로 EntityManager를 생성해주는 팩토리 클래스이다.
         *
         * 💡 내부적으로 데이터베이스 커넥션 풀, JPA 설정 정보, 엔티티 메타데이터 등을 기반으로 JPA 환경을 초기화하며,
         *    한번 생성한 후 애플리케이션이 종료될 때까지 재사용한다.
         *    - 비용이 크기 때문에 반드시 "앱 전체에서 하나만 생성"해야 한다.
         */
        EMF = Persistence.createEntityManagerFactory("jpa-config");
    }
    public static void main(String[] args) {
        /*
         * 📌 EntityManager는 실제 DB 작업(조회, 저장, 수정, 삭제 등)을 담당하는 객체이다.
         * 💡 내부적으로는 '영속성 컨텍스트(Persistence Context)'를 생성하여 엔티티를 관리하고 트랜잭션 단위로 동작한다.
         * 🎯 엔티티를 관리한다는 것은 메모리 상에서 객체 상태 변화를 추적하고, 트랜잭션 커밋 시 DB 반영 여부를 자동으로 결정하는 기능까지 포함한다.
         * 📍 참고: EntityManager는 스레드 간 공유 금지! (각 스레드 또는 요청마다 생성)
         */
        EntityManager em = EMF.createEntityManager();

        Role role = em.getReference(Role.class, 1);
        Role role1 = em.getReference(Role.class, 1);
        System.out.println(role == role1);


        // 트랜잭션 단위 작업이 끝났다면 EntityManager는 반드시 닫아줘야 한다
        em.clear();
        // EntityManagerFactory는 애플리케이션 종료 시에만 닫아야 한다.
        EMF.close();
    }

}
