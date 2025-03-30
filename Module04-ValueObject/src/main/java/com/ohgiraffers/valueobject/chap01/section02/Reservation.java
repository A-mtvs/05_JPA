package com.ohgiraffers.valueobject.chap01.section02;


import jakarta.persistence.*;


/*
 * 📌 @Embedded 어노테이션
 * - 이 필드는 JPA의 임베디드 타입(Embedded Type)을 포함하는 필드임을 나타낸다.
 * - 임베디드 타입은 @Embeddable로 정의된 값 객체(Value Object)로, 독립적인 엔티티가 아니라 포함된 엔티티의 일부로 사용된다.
 *   즉, 두 객체가 합쳐서 하나의 테이블을 형성한다는 것을 의미.
 *
 * 📌 사용 목적
 * - 관련 데이터를 하나의 객체로 묶어 재사용성과 코드 가독성을 높인다.
 * - 예: Address와 같은 임베디드 타입을 Order, Customer 등 여러 엔티티에서 재사용 가능.
 *
 * 📌 동작 방식
 * - @Embedded로 지정된 필드는 @Embeddable 클래스의 필드들로 분해되어, 포함된 엔티티의 테이블에 컬럼으로 매핑된다.
 * - 별도의 테이블이 생성되지 않으며, 임베디드 타입의 필드가 포함된 엔티티의 테이블에 직접 포함된다.
 * - 예: Address 클래스의 city, street, zipcode 필드가 Order 테이블의 컬럼으로 매핑됨.
 *
 * 📌 추가 설정
 * - @AttributeOverrides를 사용하여 임베디드 타입의 필드 이름을 커스터마이징 가능.
 * - 예: Address의 city 필드를 delivery_city라는 컬럼 이름으로 매핑.
 *
 * 📌 주의사항
 * - 임베디드 타입은 독립적인 식별자(@Id)를 가질 수 없으며, 포함된 엔티티의 생명주기에 의존한다.
 * - 불변 객체로 설계하는 것이 권장되며, Setter 메서드를 제공하지 않는 것이 좋다.
 */

/*
 * Reservation 엔티티 생성 및 값 객체 활용
 * - Reservation 엔티티는 StayPeriod와 GuestCount 값 객체를 포함하고 있다.
 * - Reservation 객체를 생성할 때, 이미 StayPeriod와 GuestCount 객체가 생성되는 과정에서
 *   정의된 비즈니스 규칙들이 적용되어 데이터의 유효성을 일차적으로 검증할 수 있다.
 * - calculateTotalPrice() 메서드에서도 stayPeriod 값 객체의 정보를 활용하여 총 가격을 계산한다.
 */
@Entity
@Table(name = "hotel_reservation") // 테이블명 변경
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guestName;
    private String roomNumber;

    @Embedded
    private GuestCount numberOfGuests; // 📌 GuestCount 값 객체

    @Embedded
    private StayPeriod stayPeriod; // 📌 StayPeriod 값 객체

    private int roomRate; // 1박당 방 가격

    protected Reservation() {}

    public Reservation(String guestName, String roomNumber, GuestCount numberOfGuests, StayPeriod stayPeriod, int roomRate) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.numberOfGuests = numberOfGuests;
        this.stayPeriod = stayPeriod;
        this.roomRate = roomRate;
    }

    public int calculateTotalPrice() {
        return stayPeriod.getNights() * roomRate;
    }

    public Long getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public GuestCount getNumberOfGuests() {
        return numberOfGuests;
    }

    public StayPeriod getStayPeriod() {
        return stayPeriod;
    }

    public int getRoomRate() {
        return roomRate;
    }
}