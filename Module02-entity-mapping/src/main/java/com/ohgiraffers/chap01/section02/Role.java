package com.ohgiraffers.chap01.section02;


/*
 * 📌 Enum 타입 선언
 *
 * ▶ Enum은 자바의 타입 안전성(type-safety)을 제공하면서
 *    제한된 값만 사용할 수 있도록 강제한다.
 *
 * ▶ Enum은 여러 관련 상수를 그룹화하여 정의할 수 있는 특별한 데이터 타입이다.
 *    사용자가 정의한 상수들의 집합을 생성할 수 있다.
 */
public enum Role {
    // Enum 상수: 각 상수는 대문자로 표기하며,
    // 이들은 Role 타입의 인스턴스이다.
    STUDENT,    // 학생 역할
    INSTRUCTOR, // 강사 역할
    ADMIN       // 관리자 역할
    ;

    // 추가적인 속성을 정의할 수 있다.
    // 예를 들어, 각 역할의 설명을 담기 위해 필드를 추가할 수 있다.
    // private final String description;

    // 생성자를 통해 각 상수가 가질 수 있는 값을 정의할 수 있다.
    // 예: Role(String description) { this.description = description; }

    // 각 상수에 대한 설명을 리턴하는 메서드를 추가할 수 있다.
    // public String getDescription() { return description; }

}

/*
 * ▶ Enum의 장점
 *
 * 1. 타입 안전성: Enum 타입을 사용하여 잘못된 값의 할당을 방지한다.
 *    예: Role role = Role.STUDENT; // 올바른 사용
 *
 * 2. 가독성: 코드의 의미를 명확하게 전달한다.
 *
 * 3. 네임스페이스: Role.STUDENT와 같이 네임스페이스를 사용하여
 *    이름 충돌을 피할 수 있다.
 *
 * 4. 반복문 사용: Enum의 모든 값을 반복할 수 있다.
 *    예: for (Role role : Role.values()) { ... }
 *
 * 5. switch 문에서 사용 가능: Enum 상수를 switch 문에서 사용할 수 있다.
 */
