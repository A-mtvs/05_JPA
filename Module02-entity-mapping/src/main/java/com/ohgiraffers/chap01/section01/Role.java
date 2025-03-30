package com.ohgiraffers.chap01.section01;

import jakarta.persistence.*;

/*
 * 📌 @Entity
 *   - 해당 클래스가 JPA가 관리할 수 있는 "엔티티 클래스"임을 명시
 *   - 이 어노테이션이 있어야 JPA는 객체를 테이블로 인식하고, SQL을 자동 생성할 수 있음
 *   - 반드시 기본 생성자 존재해야 함 (JPA가 리플렉션으로 객체 생성)
 *   - final 클래스나 필드에 붙이면 안 됨
 */
@Entity

/*
 * 📌 @Table
 *   - 매핑할 테이블 이름을 명시
 *   - 생략하면 클래스 이름을 테이블로 인식 (User → USER)

 * ▶ 주요 속성:
 *   - name: 실제 테이블 이름
 *   - schema: 스키마 지정 (PostgreSQL 등에서 사용)
 *   - uniqueConstraints: 복합 유니크 제약조건 설정
 *   - indexes: 인덱스 설정
 */
@Table(name = "roles")
class Role {
    /*
     * 📌 @Id + @GeneratedValue
     * ▶ 역할:
     *   - 엔티티의 기본 키(PK) 지정
     *   - 자동 증가 전략(IDENTITY, SEQUENCE, TABLE, AUTO) 선택 가능
     * ▶ GenerationType :
     *   - IDENTITY: DB가 자동 증가 (MySQL)
     *   - SEQUENCE: DB 시퀀스 객체 사용 (Oracle, PostgreSQL)
     *   - TABLE: 별도 테이블로 키 관리 (성능 느림)
     *   - AUTO: DB에 따라 자동 선택
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;


    /*
     * 📌 @Column 어노테이션
     * ▶ 역할:
     *   - 필드를 테이블의 컬럼으로 매핑
     *
     * ▶ 주요 속성:
     *   - name: 컬럼명
     *   - nullable: NULL 허용 여부
     *   - unique: 유일값 여부
     *   - length: 문자 길이 제한 (문자열에만 적용됨)
     */
    @Column(name = "role_name", nullable = false, unique = true, length = 50)
    private String name;

    protected Role() {}

    public Role(String name) {
        this.name = name;
    }

    // Getter 생략
}
