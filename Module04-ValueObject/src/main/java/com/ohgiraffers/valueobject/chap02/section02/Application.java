package com.ohgiraffers.valueobject.chap02.section02;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/*
 * 📌 @ElementCollection
 * - 관계형 데이터베이스는 컬렉션 자체를 저장하는 기능을 직접적으로 제공하지 않는다
 * - 따라서 엔티티 내에 컬렉션 타입의 필드를 저장하려면, JPA는 이를 데이터베이스 테이블에 적절히 매핑해야 한다.
 *
 * 💡 패러다임 불일치 해소: 객체 모델 vs 관계형 모델
 * - 객체 지향 프로그래밍에서는 객체 간의 관계를 탐색하거나 객체 내부에 다른 객체의 컬렉션을 포함하는 것이 자연스럽다.
 * - 하지만 관계형 데이터베이스는 테이블 간의 외래 키를 통한 연관 관계는 잘 지원하지만, 객체 내부의 컬렉션을 직접 표현하기는 어렵다.
 *
 * - `@ElementCollection`은 이러한 객체 모델과 관계형 모델 간의 패러다임 불일치를 해소하는 중요한 메커니즘을 제공한다
 * - 이를 통해 개발자는 엔티티 내에 값 타입(Value Object)의 컬렉션을 자연스럽게 정의하고 사용할 수 있으며,
 * JPA는 이 컬렉션을 데이터베이스에 저장하고 관리하는 방식을 자동으로 처리하게 된다.
 *
 * 🛠️ 동작 방식
 * - `@ElementCollection`이 적용된 컬렉션 필드는 JPA에 의해 별도의 테이블로 매핑.
 * - `@CollectionTable` 어노테이션을 사용하여 이 별도 테이블의 이름과 외래 키 설정을 지정.
 * - 컬렉션에 저장되는 요소는 일반적으로 `@Embeddable`로 정의된 값 객체이거나, 단순한 기본 자료형(String, Integer 등)일 수 있다.
 * - JPA는 소유 엔티티와 값 타입 컬렉션 간의 생명 주기를 관리하며, 엔티티가 영속화될 때 컬렉션의 요소들도 함께 영속화되고,
 * 엔티티가 삭제될 때 컬렉션의 요소들도 함께 삭제하는 등의 작업을 수행.
 *
 * 📌 장점 및 @OneToMany를 사용하지 않는 이유
 * - 값 타입의 의미 강조:
 *      `@ElementCollection`은 컬렉션의 요소가 독립적인 엔티티가 아닌, 소유 엔티티의 속성인 값 타입임을 명확히 나타낸다.
 *      이는 도메인 모델을 더 정확하게 표현한다. `@OneToMany`는 연관된 엔티티 간의 관계를 표현하는 데 사용된다.
 *
 * - 단순한 데이터 관리:
 *      선호하는 색상이나 가능한 사이즈와 같은 값 타입은 일반적으로 독립적인 생명 주기를 가지지 않고 소유 엔티티에 의존한다.
 *      `@ElementCollection`은 이러한 종속적인 값들의 컬렉션을 관리하는 데 더 적합하며, 불필요한 엔티티 관리를 줄여준다.
 *      `@OneToMany`는 연관된 엔티티 각각의 생명 주기를 관리해야 한다.
 *
 * - 동등성 비교의 용이성 :
 *      값 타입은 `equals()`와 `hashCode()` 메서드를 재정의하여 값 기반의 동등성 비교를 쉽게 구현할 수 있다.
 *      `@ElementCollection`은 이러한 값 타입의 컬렉션을 다루는 데 자연스럽다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {
            // 상품 엔티티 생성
            Product product1 = new Product("멋쟁이 티셔츠");

            // AvailableSize 값 객체 생성
            AvailableSize sizeS = new AvailableSize("S", 10);
            AvailableSize sizeM = new AvailableSize("M", 20);
            AvailableSize sizeL = new AvailableSize("L", 15);

            // 값 객체 추가
            product1.addAvailableSize(sizeS);
            product1.addAvailableSize(sizeM);
            product1.addAvailableSize(sizeL);

            // 영속화: Product를 영속화하면 @ElementCollection 설정에 따라 AvailableSize도 함께 저장됨
            em.persist(product1);

            /*
             * ✅ 해결 1: 불필요한 엔티티 관리 제거
             * - @OneToMany 설계에서는 AvailableSize가 @Entity로 관리되어,
             *   단순한 값 변경(재고 감소)에도 JPA의 변경 추적(Dirty Checking)이 발생.
             * - @ElementCollection으로 변경하면 AvailableSize가 값 객체로 관리되므로,
             *   JPA가 이를 독립적인 엔티티로 관리하지 않음.
             * - 변경 추적은 Product 엔티티에만 적용되며, AvailableSize는 Product의 일부로 관리됨.
             */
            product1.decreaseStock("M", 5); // jpa에서 관리하지 않음
            System.out.println("티셔츠 M 사이즈 재고 감소 후: " + product1.getAvailableSizes());

            /*
             * ✅ 해결 2: 동등성 비교의 용이성 (값 기반 비교)
             * - @OneToMany 설계에서는 AvailableSize가 @Entity로 관리되어 JPA가 ID를 기준으로 비교.
             * - 동일한 라벨("M")을 가진 새로운 객체를 생성하더라도 ID가 다르거나 영속성 컨텍스트에 없으면 동일한 객체로 인식되지 않음.
             * - @ElementCollection으로 변경하면 AvailableSize가 값 객체로 관리되며,
             *   equals 메서드를 통해 라벨을 기준으로 값 기반 비교 가능.
             */
            AvailableSize searchM = new AvailableSize("M", 0);
            boolean containsM = product1.getAvailableSizes().contains(searchM);
            System.out.println("티셔츠에 M 사이즈가 포함되어 있는가 (값 기반 비교)? " + containsM); // true

            /*
             * ✅ 해결 3: 컬렉션 조작의 간소화
             * - @OneToMany 설계에서는 특정 사이즈를 제거하려면 정확한 AvailableSize 인스턴스를 알아야 함.
             * - @ElementCollection으로 변경하면 값 객체의 equals 메서드를 통해 라벨을 기준으로 제거 가능.
             * - 예: 라벨이 "M"인 AvailableSize 객체를 새로 생성하여 제거 가능.
             */
            product1.removeAvailableSize(new AvailableSize("M", 0));
            System.out.println("티셔츠에서 M 사이즈 제거 후 사이즈 목록: " + product1.getAvailableSizes());

            /*
             * 새로운 "L" 사이즈 추가
             * - @OneToMany 설계에서는 동일한 라벨("L")을 가진 데이터가 중복으로 저장됨.
             * - @ElementCollection으로 변경하면 중복을 방지하기 위해 추가 로직을 구현 가능.
             * - 예: 추가 전에 라벨이 이미 존재하는지 확인.
             */
            AvailableSize newSizeL = new AvailableSize("L", 10);
            boolean alreadyExists = product1.getAvailableSizes().stream()
                    .anyMatch(size -> size.getLabel().equals(newSizeL.getLabel()));
            if (!alreadyExists) {
                product1.addAvailableSize(newSizeL);
                System.out.println("티셔츠에 새로운 L 사이즈 추가 후 사이즈 목록: " + product1.getAvailableSizes());
            } else {
                System.out.println("이미 L 사이즈가 존재합니다: " + product1.getAvailableSizes());
            }

            /*
             * ✅ 해결 4: 양방향 연관관계 관리 불필요
             * - @OneToMany 설계에서는 양방향 연관관계를 관리해야 하며,
             *   addAvailableSize와 removeAvailableSize 메서드에서 연관관계를 일관되게 유지해야 함.
             * - @ElementCollection으로 변경하면 단방향 관계로 관리되므로,
             *   양방향 연관관계를 설정하거나 해제할 필요가 없음.
             * - 코드가 간결해지고, 연관관계 불일치로 인한 오류 가능성 감소.
             */

            em.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 값입니다: " + e.getMessage());
            em.getTransaction().rollback();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
