package com.ohgiraffers.chap02.section02;

import com.ohgiraffers.chap02.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");

        /* 각 생명주기 테스트 메서드 호출 */

        // 비영속 - 해당 엔티티는 단순한 java의 객체이다.
//        testNewState(emf);

        // 영속 상태 - 해당 엔티티는 영속성 컨텍스트에서 관리가 되는 객체로 값이 DB에 저장된다.
//        testManagedState(emf);

        // 준영속 상태 - 준영속 이전 시점의 상태를 저장하고 DB에 반영함
//        testDetachedState(emf);


        // 삭제 상태 - 영속화 이후 삭제가 되면서 데이터베이스의 값을 제거함.
        // 쿼리 로그를 보면 insert와 delete 모두 호출된다.
//        testRemovedState(emf);

        // 준영속 상태 -> merge : 영속화 이후
//        testMergeAfterDetached(emf);

        emf.close();
    }

    /**
     * 비영속 상태(New) 테스트
     * - 엔티티 객체가 JPA와 연관되지 않은 상태를 확인합니다.
     */
    private static void testNewState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /*
         * 📌 비영속 상태(New)
         * - `Role` 객체를 생성했지만, 아직 JPA의 영속성 컨텍스트와 연관되지 않은 상태이다.
         * - JPA는 이 객체를 관리하지 않으며, 데이터베이스와도 연결되지 않는다.
         * - 이 상태에서 객체를 수정하거나 삭제해도 JPA나 데이터베이스에 영향을 주지 않는다.
         */
        Role role = new Role("비영속 권한");
        System.out.println("비영속 상태 - Role: " + role);

        // 비영속 상태에서는 JPA가 관여하지 않으므로 변경 사항이 데이터베이스에 반영되지 않음
        role.setRoleName("변경된 비영속 권한");

        em.getTransaction().commit();
        em.close();
    }

    /**
     * 영속 상태(Managed) 테스트
     * - 엔티티가 영속성 컨텍스트에 등록되어 JPA가 관리하는 상태를 확인.
     */
    private static void testManagedState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();


        Role role = new Role("영속 권한");
        /*
         * 📌 영속 상태(Managed)
         * - `em.persist(role)`를 호출하여 `Role` 객체가 영속성 컨텍스트에 등록된다.
         * - JPA는 이 객체를 1차 캐시에 보관하며, 상태 변화를 추적(Dirty Checking)한다.
         * - 트랜잭션 커밋 시 `INSERT` 쿼리가 실행되어 데이터베이스에 반영된다.
         */
        em.persist(role);
        System.out.println("영속 상태 - Role: " + role);

        // 영속 상태에서는 변경 감지가 이루어지므로, 수정 사항이 트랜잭션 커밋 시 데이터베이스에 반영됨
        role.setRoleName("변경된 영속 권한");
        em.getTransaction().commit();
        em.close();
    }

    /**
     * 준영속 상태(Detached) 테스트
     * - 영속 상태에서 준영속 상태로 전환된 엔티티의 동작을 확인.
     */
    private static void testDetachedState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /*
         * 📌 준영속 상태(Detached)
         * - `em.persist(role)`로 영속 상태가 된 객체를 `em.detach(role)`로 준영속 상태로 전환합니다.
         * - 준영속 상태에서는 영속성 컨텍스트에서 분리되어 더 이상 JPA가 변경을 추적하지 않습니다.
         * - 그러나 `persist`로 인해 이미 "INSERT 예정" 작업이 등록되었으므로,
         *   트랜잭션 커밋 시 `INSERT` 쿼리가 실행됩니다.
         * - 준영속 상태에서 변경 사항(예: `role.setRoleName()`)은 반영되지 않습니다.
         */
        Role role = new Role("준영속 권한");
        em.persist(role);
        System.out.println("영속 상태 - Role: " + role);

        // 준영속 상태로 전환
        em.detach(role);
        System.out.println("준영속 상태 - Role: " + role);

        // 준영속 상태에서는 변경 감지가 이루어지지 않으므로, 이 변경은 데이터베이스에 반영되지 않음
        role.setRoleName("변경된 준영속 권한");

        em.getTransaction().commit(); // INSERT 쿼리 실행 (persist 시점의 데이터로 반영)
        em.close();
    }

    /**
     * 삭제 상태(Removed) 테스트
     * - 엔티티를 삭제 상태로 전환하고 데이터베이스에서 삭제되는 과정을 확인.
     */
    private static void testRemovedState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /*
         * 📌 삭제 상태(Removed)
         * - `em.persist(role)`로 영속 상태가 된 객체를 `em.remove(role)`로 삭제 상태로 전환한다.
         * - 삭제 상태에서는 트랜잭션 커밋 시 `DELETE` 쿼리가 실행되어 데이터베이스에서 해당 레코드가 삭제된다.
         * - 단, 데이터베이스에 아직 저장되지 않은 객체(예: `persist` 후 커밋 전)에 `remove`를 호출하면,
         *   `INSERT`와 `DELETE`가 모두 실행되지 않을 수 있다.
         */
        Role role = new Role("삭제 권한");
        em.persist(role);
        System.out.println("영속 상태 - Role: " + role);

        // 삭제 상태로 전환
        em.remove(role);
        System.out.println("삭제 상태 - Role: " + role);

        em.getTransaction().commit(); // DELETE 쿼리 실행 (단, persist 후 바로 remove된 경우 아무 작업도 실행되지 않을 수 있음)
        em.close();
    }

    /**
     * 준영속 상태에서 Merge 테스트
     * - 준영속 상태의 엔티티를 다시 영속 상태로 전환하는 과정을 확인합니다.
     */
    private static void testMergeAfterDetached(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /*
         * 📌 준영속 상태에서 Merge
         * - 준영속 상태의 객체는 영속성 컨텍스트에서 분리되어 JPA가 관리하지 않는다.
         * - `em.merge(role)`를 호출하면 준영속 상태의 객체를 다시 영속 상태로 전환한다.
         * - `merge`는 데이터베이스의 현재 상태와 비교하여 변경 사항을 반영하며,
         *   새로운 영속 상태의 객체를 반환한다(원본 객체와 다를 수 있음).
         * - 트랜잭션 커밋 시 변경 사항이 데이터베이스에 반영된다.
         */
        Role role = new Role("Merge 테스트 권한");
        em.persist(role);
        em.getTransaction().commit(); // INSERT 실행
        System.out.println("영속 상태 - Role: " + role);

        // 새로운 트랜잭션 시작
        em.getTransaction().begin();

        // 준영속 상태로 전환
        em.detach(role);
        System.out.println("준영속 상태 - Role: " + role);

        // 준영속 상태에서 변경
        role.setRoleName("Merge로 변경된 권한");

        // Merge를 통해 다시 영속 상태로 전환
        Role mergedRole = em.merge(role);
        System.out.println("Merge 후 영속 상태 - Role: " + mergedRole);
        System.out.println("원본과 동일한 객체인가? " + (role == mergedRole)); // false일 가능성 있음

        em.getTransaction().commit(); // UPDATE 쿼리 실행 (변경된 데이터 반영)
        em.close();
    }
}
