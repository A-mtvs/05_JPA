package com.ohgiraffers.inheritance.chap01.section03;



/*
 * 📌 JOINED 전략 설명
 * - 부모와 자식 클래스를 각각 테이블로 나누고, 조인으로 연결.
 * - 장점: 테이블이 정규화되어 데이터 중복이 적고, 각 테이블이 독립적.
 * - 단점: 조회 시 조인이 필요하므로 성능이 느려질 수 있음.
 *
 * 📌 DB에서 공통 속성의 테이블 분산
 * - 데이터베이스에서는 부모 클래스(ProductJoined)의 속성을 products_joined 테이블에 저장.
 * - 각 자식 클래스의 속성은 별도의 테이블(electronic_products_joined, clothing_products_joined, food_products_joined)에 저장.
 * - 조회 시 부모 테이블과 자식 테이블을 조인하여 데이터를 가져옴.
 * - 예: ElectronicProduct 조회 시 products_joined와 electronic_products_joined를 조인.
 * - 테이블 구조:
 *   - products_joined(id, name, price, brand, stock_quantity, product_type)
 *   - electronic_products_joined(id, warranty_period, power_consumption)
 *   - clothing_products_joined(id, size, material, color)
 *   - food_products_joined(id, expiration_date, is_organic, storage_instruction)
 * - 조인 쿼리 예: SELECT * FROM products_joined p JOIN electronic_products_joined e ON p.id = e.id WHERE p.product_type = 'ELECTRONIC'
 *
 * 📌 실생활 비유
 * - 공통 정보(상품명, 가격)는 "상품 기본 정보" 창고에 저장.
 * - 세부 정보(보증 기간, 사이즈, 유통기한)는 각각 "전자제품", "의류", "식품" 창고에 나눠 저장.
 * - 필요할 때 창고를 조합하여 상품 정보를 가져옴.
 */

import com.ohgiraffers.inheritance.chap01.section03.model.ClothingProductJoined;
import com.ohgiraffers.inheritance.chap01.section03.model.ElectronicProductJoined;
import com.ohgiraffers.inheritance.chap01.section03.model.FoodProductJoined;
import com.ohgiraffers.inheritance.chap01.section03.model.ProductJoined;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;



/*
 * 📌 JOINED 전략 설명
 * - 부모와 자식 클래스를 각각 테이블로 나누고, 조인으로 연결.
 * - 장점: 테이블이 정규화되어 데이터 중복이 적고, 각 테이블이 독립적.
 * - 단점: 조회 시 조인이 필요하므로 성능이 느려질 수 있음.
 *
 * 📌 DB에서 공통 속성의 테이블 분산
 * - 데이터베이스에서는 부모 클래스(ProductJoined)의 속성을 products_joined 테이블에 저장.
 * - 각 자식 클래스의 속성은 별도의 테이블(electronic_products_joined, clothing_products_joined, food_products_joined)에 저장.
 * - 조회 시 부모 테이블과 자식 테이블을 조인하여 데이터를 가져옴.
 * - 예: ElectronicProduct 조회 시 products_joined와 electronic_products_joined를 조인.
 * - 테이블 구조:
 *   - products_joined(id, name, price, brand, stock_quantity, product_type)
 *   - electronic_products_joined(id, warranty_period, power_consumption)
 *   - clothing_products_joined(id, size, material, color)
 *   - food_products_joined(id, expiration_date, is_organic, storage_instruction)
 * - 조인 쿼리 예: SELECT * FROM products_joined p JOIN electronic_products_joined e ON p.id = e.id WHERE p.product_type = 'ELECTRONIC'
 *
 * 📌 실생활 비유
 * - 공통 정보(상품명, 가격)는 "상품 기본 정보" 창고에 저장.
 * - 세부 정보(보증 기간, 사이즈, 유통기한)는 각각 "전자제품", "의류", "식품" 창고에 나눠 저장.
 * - 필요할 때 창고를 조합하여 상품 정보를 가져옴.
 */

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {
            // 전자제품 저장
            ElectronicProductJoined electronic = new ElectronicProductJoined("Laptop", 999.99, "TechBrand", 50, 24, "65W");
            em.persist(electronic);

            // 의류 저장
            ClothingProductJoined clothing = new ClothingProductJoined("T-Shirt", 19.99, "FashionBrand", 100, "M", "Cotton", "Blue");
            em.persist(clothing);

            // 식품 저장
            FoodProductJoined food = new FoodProductJoined("Milk", 2.99, "FoodBrand", 200, LocalDate.now().plusDays(7), true, "Refrigerate at 4°C");
            em.persist(food);

            em.flush();
            em.clear();

            // 모든 상품 조회
            System.out.println("JOINED 전략으로 조회:");
            em.createQuery("SELECT p FROM ProductJoined p", ProductJoined.class)
                    .getResultList()
                    .forEach(System.out::println);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
