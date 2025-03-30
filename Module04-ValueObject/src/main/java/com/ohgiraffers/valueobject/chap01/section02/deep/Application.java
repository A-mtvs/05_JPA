package com.ohgiraffers.valueobject.chap01.section02.deep;


import com.ohgiraffers.valueobject.chap01.section02.GuestCount;
import com.ohgiraffers.valueobject.chap01.section02.StayPeriod;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * 📌 DeepDive: 값 타입(Value Object)의 메모리 구조와 동작 이해
 *
 * ✔️ 본 파일은 JPA에서 임베디드 타입(@Embeddable)이 힙과 스택 메모리에서 어떻게 동작하는지,
 *    equals/hashCode 재정의가 왜 필요한지, 불변성 설계가 어떤 이점을 주는지를 실습으로 확인한다.
 */
public class Application {
    public static void main(String[] args) {
        /*
         * 🎯 equals & hashCode 재정의의 중요성 (동등성 비교)
         * - 값 객체는 값이 같으면 논리적으로 같은 객체로 간주되어야 합니다.
         * - equals()를 재정의하지 않으면 객체의 참조 주소를 비교하게 되어 값이 같더라도 다르다고 판단합니다.
         */
        GuestCount guestCount1 = new GuestCount(2);
        GuestCount guestCount2 = new GuestCount(2);

        System.out.println("주소 값을 기준으로 (equals 미재정의): " + (guestCount1 == guestCount2)); // false (참조 비교)
        System.out.println("값을 기준으로 (equals 재정의): " + guestCount1.equals(guestCount2)); // true (값 비교)

        /*
         * 📌 equals & hashCode 재정의의 중요성 (컬렉션 중복 제거)
         * - hashCode()를 올바르게 재정의하지 않으면 HashSet과 같은 컬렉션에서 같은 값을 가진 객체를 중복으로 저장할 수 있다.
         */
        Set<GuestCount> guestSetWithHashCode = new HashSet<>();
        guestSetWithHashCode.add(guestCount1);
        guestSetWithHashCode.add(guestCount2); // equals()와 hashCode() 재정의로 중복 제거됨

        System.out.println("HashSet 크기 (hashCode 재정의): " + guestSetWithHashCode.size()); // 1 (정상적인 중복 제거)

        /*
         * 📌 불변성 설계 (Immutable Object)의 이점
         * - GuestCount와 StayPeriod는 생성 시점 이후 내부 상태를 변경할 수 있는 setter 메서드를 제공하지 않는다.
         * - 이는 다음과 같은 이점을 제공하게 된다.
         * 1. 안전성 향상: 객체가 생성된 후에는 값이 변하지 않으므로 예기치 않은 Side Effect 발생 가능성을 줄인다.
         * 2. 예측 가능성: 객체의 상태가 항상 일정하므로 코드를 이해하고 예측하기 쉬워진다.
         * 3. 멀티스레드 환경 안전: 여러 스레드에서 동시에 접근해도 데이터 불변성으로 인해 안전하다.
         * 4. equals/hashCode 기반 비교의 안정성 확보: 객체의 상태가 변하지 않으므로 equals()와 hashCode()의 결과가 일관성을 유지한다.
         */
        StayPeriod period1 = new StayPeriod(
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 3)
        );
        System.out.println("최초 숙박일 수: " + period1.getNights() + "박");

        // period1.setCheckOutDate(LocalDate.of(2025, 5, 5)); // setter가 없으므로 컴파일 에러 발생 (불변성 유지)

        StayPeriod period2 = new StayPeriod(period1.getCheckInDate(), LocalDate.of(2025, 5, 5)); // 새로운 불변 객체 생성

        System.out.println("새로운 숙박일 수: " + period2.getNights() + "박");
        System.out.println("기존 숙박일 수 (불변): " + period1.getNights() + "박"); // 기존 객체의 상태는 변하지 않음

        /*
         * 📌 실무에서 주의할 점
         * - 값 객체는 절대 공유되지 않아야 한다 (Side Effect 방지)
         * - 복합 필드(객체 필드)를 갖는 값 타입은 깊은 복사를 고려해야 한다
         * - 동일 값 비교 시 반드시 equals/hashCode를 재정의해야 한다
         * - 임베디드 타입은 DB에 별도 테이블이 생기지 않음 (동일 테이블에 컬럼으로 포함)
         */

    }
}
