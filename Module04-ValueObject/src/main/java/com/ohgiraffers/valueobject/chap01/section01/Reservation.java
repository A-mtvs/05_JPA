package com.ohgiraffers.valueobject.chap01.section01;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*
* 값 타입의 필요성을 알아본다.
* */

public class Reservation {

    private String guestName;
    private String roomNumber;
    private int numberOfGuests;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalPrice;

    public Reservation(String guestName, String roomNumber, int numberOfGuests, LocalDate checkInDate, LocalDate checkOutDate, int totalPrice) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.numberOfGuests = numberOfGuests;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    public int calculateNights() {
        // 😥 단순히 두 날짜의 차이를 계산할 뿐,
        // 체크인 날짜가 체크아웃 날짜보다 늦는 경우에 대한 검증이 없다.
        // 만약 이쪽에 체크인 체크아웃을 검증하는 프로세스를 추가하여도
        // 해당 체크인 체크아웃의 데이터를 필요로하는 모든 객체에서도 해당 코드를 작성해야 한다.
        // EX) 고객 관리 시스템에서는 고객의 예약을 확인할 때 체크인 및 체크아웃 날짜의 유효성을 검증해야 한다.
        return (int) checkOutDate.toEpochDay() - (int) checkInDate.toEpochDay();
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        // 😥 총 가격에 대한 어떠한 제약 조건도 없으며
        // 이는 음수 값이나 0이 될 수도 있다.
        this.totalPrice = totalPrice;
    }
}