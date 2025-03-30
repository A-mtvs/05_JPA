package com.ohgiraffers.inheritance.chap01.section01;

import com.ohgiraffers.inheritance.chap01.section01.model.ClothingProduct;
import com.ohgiraffers.inheritance.chap01.section01.model.ElectronicProduct;
import com.ohgiraffers.inheritance.chap01.section01.model.FoodProduct;

import java.time.LocalDate;


/*
 * 📌 상속 매핑이 없으면 발생하는 문제
 * - 온라인 쇼핑몰에서 상품(Product)을 관리한다고 가정하자.
 * - 상품에는 전자제품(ElectronicProduct), 의류(ClothingProduct), 식품(FoodProduct)이 존재한다.
 * - 상속 없이 각각의 상품을 별도의 클래스로 관리하면 중복 코드가 발생하고, 공통 속성을 관리하기 어렵다.
 * - 또한, 데이터베이스 테이블도 각각 별도로 생성해야 하므로 테이블 간 관계 관리가 복잡해진다.
 */


/*
 * 📌 잘못된 설계: 상속 없이 별도의 클래스로 관리
 * - ElectronicProduct, ClothingProduct, FoodProduct이 공통 속성(예: name, price)을 가지지만, 별도의 클래스로 관리.
 * - 공통 속성을 각각 정의해야 하므로 코드 중복 발생.
 * - 데이터베이스에서도 별도의 테이블로 관리되므로, 공통 속성을 조회하거나 관리하기 위한 추가 로직 필요.
 */
public class Application {
    public static void main(String[] args) {
        // 상속 없이 객체 생성
        ElectronicProduct electronic = new ElectronicProduct("Laptop", 999.99, "TechBrand", 50, 24);
        ClothingProduct clothing = new ClothingProduct("T-Shirt", 19.99, "FashionBrand", 100, "M", "Cotton");
        FoodProduct food = new FoodProduct("Milk", 2.99, "FoodBrand", 200, LocalDate.now().plusDays(7), true);

        System.out.println(electronic);
        System.out.println(clothing);
        System.out.println(food);

        // 상속을 사용하지 않으면 공통 속성을 관리하기 위한 추가 로직 필요
        // 예: 모든 상품의 가격을 조회하려면 세 클래스를 각각 처리해야 함

        /*
         * 📌 문제점
         * - name, price, brand, stockQuantity가 중복 정의됨.
         * - 데이터베이스에서도 electronic_products, clothing_products, food_products 테이블을 별도로 생성해야 함.
         * - 공통 속성을 조회하려면 세 테이블을 각각 조회해야 하므로 쿼리 복잡도 증가.
         * - 새로운 상품 유형(예: BookProduct)이 추가될 때마다 새로운 클래스를 만들어야 함.
         */

        /*
         * 📌 해결: 상속 매핑 사용
         * - 상속을 사용하면 공통 속성을 부모 클래스(Product)에서 정의하고, 자식 클래스에서 특화된 속성만 정의.
         * - JPA의 상속 매핑 전략을 사용하면 데이터베이스 테이블 설계도 유연해짐.
         */
    }
}
