package com.ohgiraffers.chap01.section03;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

/*
 * 📌 Money 클래스 – 복합 값 타입 (가격, 통화 정보 포함)
 *
 * ▶ 이 클래스는 JPA에 의해 테이블의 필드로 분해되어 저장됩니다.
 * ▶ JPA는 @Embeddable이 붙은 클래스의 필드들을 "product_price_amount", "product_price_currency"처럼 컬럼으로 처리
 */
@Embeddable
public class Money {

    @Column(name = "price_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "price_currency", length = 10)
    private String currency;

    protected Money() {}

    public Money(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
