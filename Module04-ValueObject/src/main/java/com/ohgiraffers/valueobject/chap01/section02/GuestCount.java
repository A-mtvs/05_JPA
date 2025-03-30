package com.ohgiraffers.valueobject.chap01.section02;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/*
 * 📌 @Embeddable 어노테이션
 * - 임베디드 타입은 독립적인 엔티티가 아니라, 다른 엔티티에 포함되어 사용되는 값 객체(Value Object)이다.
 * - 데이터베이스 테이블에서 별도의 테이블로 생성되지 않고, 포함된 엔티티의 테이블에 컬럼으로 매핑된다.
 *
 * 📌 사용 목적
 * - 관련 데이터를 하나의 객체로 묶어 재사용성을 높이고, 코드 가독성과 유지보수성을 개선한다.
 * - 예: 주소 정보(도시, 거리, 우편번호)를 Address 클래스로 정의하여 여러 엔티티에서 재사용 가능.
 *
 * 📌 동작 방식
 * - @Embeddable로 정의된 클래스는 @Embedded 어노테이션을 통해 엔티티에서 사용된다.
 * - 이 클래스의 필드들은 포함된 엔티티의 테이블에 컬럼으로 매핑된다.
 * - 예: Address 클래스의 city, street, zipcode 필드는 Order 테이블의 컬럼으로 매핑됨.
 *
 * 📌 주의사항
 * - JPA는 임베디드 타입에 기본 생성자를 요구하므로, protected 또는 public 기본 생성자를 반드시 정의해야 한다.
 * - 불변 객체로 설계하는 것이 권장되며, Setter 메서드를 제공하지 않는 것이 좋다.
 */

/*
 * 📌 값 객체(Value Object)의 필요성: GuestCount
 * - 투숙객 수 역시 단순한 정수형 필드로 관리할 수 있지만, GuestCount라는 값 객체를 통해
 *   '투숙객 수'라는 도메인적인 의미를 부여하고 관련 비즈니스 규칙(예: 투숙객 수는 1명 이상이어야 한다)을 적용할 수 있다.
 * - 이렇게 값 객체를 사용하면 데이터의 의미를 더 잘 드러내고, 유효성 검증 로직을 한 곳에 모아 관리하기 용이해진다.
 * - GuestCount 클래스 또한 @Embeddable로 정의되어 JPA에서 값 타입으로 인식되며,
 *   Reservation 엔티티에 @Embedded로 포함되어 사용된다.
 */
@Embeddable
public class GuestCount {

    private int value;

    protected GuestCount() {}

    public GuestCount(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("투숙객 수는 1명 이상이어야 합니다.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /*
     * 값 객체의 동등성 비교를 위한 equals 메서드 재정의
     * - 두 GuestCount 객체의 값이 동일하면 true를 반환하도록 한다.
     * - 이는 값 객체가 상태를 기반으로 동등성을 판단해야 하기 때문에 중요하다.
     * - 객체의 참조가 아닌 실제 값이 같은지를 비교한다.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GuestCount that = (GuestCount) o;
        return value == that.value;
    }

    /*
     * equals 메서드 재정의 시 hashCode 메서드도 함께 재정의해야 한다
     * - equals 메서드가 true를 반환하는 두 객체는 반드시 동일한 hashCode 값을 가져야 한다.
     * - 이는 HashMap, HashSet 등 컬렉션에서 값 객체를 올바르게 사용할 수 있도록 보장하기 위함이다.
     * - value 필드의 해시값을 이용하여 GuestCount 객체의 해시값을 생성한다.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /*
     * 불변 객체 형성에 기여하는 요소:
     * 1. setter 메서드를 제공하지 않습니다.
     * - 객체 생성 후 내부 상태(value 필드)를 변경할 수 없도록 하여 불변성을 유지한다.
     * 2. 생성 시점에 유효성 검증을 수행한다.
     * - 생성될 때부터 유효한 상태를 가지도록 보장한다.
     * 3. getValue() 메서드를 통해 내부 상태를 읽기만 할 수 있도록 제공한다.
     * - 외부에서 내부 상태를 간접적으로 변경할 수 있는 방법을 제공하지 않는다.
     *
     * 이러한 설계를 통해 GuestCount 객체는 생성된 후에는 그 상태가 변하지 않는 불변 객체의 특징을 가진다.
     * 이는 값 객체의 중요한 특징 중 하나이며, 예기치 않은 부작용을 줄이고 코드를 더욱 안전하게 만든다.
     */
}
