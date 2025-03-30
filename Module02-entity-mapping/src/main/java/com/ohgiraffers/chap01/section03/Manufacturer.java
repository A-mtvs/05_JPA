package com.ohgiraffers.chap01.section03;

import jakarta.persistence.Embeddable;

/*
 * 📌 Manufacturer – 제조사 정보도 Embedded 타입으로 구성
 * ▶ name + country 조합을 한 덩어리로 관리하는 값 객체(Value Object)
 */
@Embeddable
public class Manufacturer {
    private String name;
    private String country;

    protected Manufacturer() {}

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
