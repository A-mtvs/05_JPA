package com.ohgiraffers.inheritance.chap01.section02;



/*
 * 📌 상속 매핑 전략 설명
 * - JPA는 객체지향의 상속을 데이터베이스에 매핑하기 위해 3가지 전략을 제공한다:
 *   1. SINGLE_TABLE: 모든 클래스를 하나의 테이블에 저장. DTYPE 컬럼으로 클래스 구분.
 *   2. JOINED: 부모와 자식 클래스를 각각 테이블로 나누고, 조인으로 연결.
 *   3. TABLE_PER_CLASS: 각 클래스를 별도의 테이블로 저장.
 *
 * 📌 실생활 비유
 * - 온라인 쇼핑몰에서 상품을 관리한다고 생각해보자.
 * - SINGLE_TABLE: 모든 상품(전자제품, 의류, 식품)을 한꺼번에 "상품 목록"이라는 하나의 창고에 넣고, 라벨(DTYPE)로 구분.
 * - JOINED: 공통 정보(상품명, 가격)는 "상품 기본 정보" 창고에, 세부 정보(보증 기간, 사이즈, 유통기한)는 각각 "전자제품", "의류", "식품" 창고에 나눠 넣고 필요할 때 조합.
 * - TABLE_PER_CLASS: 전자제품, 의류, 식품을 완전히 다른 창고에 넣고, 공통 정보도 중복 저장.
 *
 * 📌 SINGLE_TABLE 전략
 * - 장점: 쿼리가 단순하고 빠름(조인 불필요).
 * - 단점: 자식 클래스의 컬럼이 많아지면 테이블이 비대해지고, NULL 값이 많아질 수 있음.
 * - 메모리: 모든 데이터가 하나의 테이블에 저장되므로, 조회 시 한 번의 디스크 I/O로 모든 데이터 로드.
 */

import com.ohgiraffers.inheritance.chap01.section02.model.ClothingProduct;
import com.ohgiraffers.inheritance.chap01.section02.model.ElectronicProduct;
import com.ohgiraffers.inheritance.chap01.section02.model.FoodProduct;
import com.ohgiraffers.inheritance.chap01.section02.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

/*
 * 📌 SINGLE_TABLE 전략 설명
 * - 모든 클래스를 하나의 테이블에 저장. DTYPE 컬럼으로 클래스 구분.
 * - 장점: 쿼리가 단순하고 빠름(조인 불필요).
 * - 단점: 자식 클래스의 컬럼이 많아지면 테이블이 비대해지고, NULL 값이 많아질 수 있음.
 *
 * 📌 DB에서 서브타입 엔티티 표현
 * - 데이터베이스에서는 모든 자식 클래스의 데이터가 하나의 테이블(products)에 저장됨.
 * - DTYPE 컬럼을 통해 각 행이 어떤 자식 클래스에 해당하는지 구분.
 * - 예: DTYPE이 "ELECTRONIC"이면 ElectronicProduct, "CLOTHING"이면 ClothingProduct, "FOOD"이면 FoodProduct.
 * - 서브타입 엔티티의 속성(warrantyPeriod, size, expirationDate 등)은 해당 행에서만 값이 채워지고, 다른 서브타입의 속성은 NULL로 남음.
 * - 결과적으로 테이블 구조는 다음과 같음:
 *   - products(id, name, price, brand, stock_quantity, product_type, warranty_period, size, material, expiration_date, is_organic)
 *   - ElectronicProduct 행: warranty_period만 값이 있고, size, material, expiration_date, is_organic은 NULL.
 *   - ClothingProduct 행: size, material만 값이 있고, warranty_period, expiration_date, is_organic은 NULL.
 *   - FoodProduct 행: expiration_date, is_organic만 값이 있고, warranty_period, size, material은 NULL.
 *
 * 📌 실생활 비유
 * - 모든 상품을 하나의 큰 창고(products)에 넣고, 라벨(product_type)로 구분.
 * - 창고의 각 칸에는 모든 상품의 속성을 위한 공간이 있지만, 해당 상품에 필요 없는 속성은 비어 있음(NULL).
 */

/*
 * 📌 부모 클래스: Product
 * - @Inheritance(strategy = InheritanceType.SINGLE_TABLE): SINGLE_TABLE 전략 사용.
 * - @DiscriminatorColumn: DTYPE 컬럼으로 자식 클래스 구분.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {
            /*
            * 가전 제품은 아래의 컬럼을 사용함
            * warrantyPeriod
            * powerConsumption
            * */
            ElectronicProduct electronic = new ElectronicProduct("Laptop", 999.99, "TechBrand", 50, 24, "65W");
            em.persist(electronic);

            /*
            * 의류는 아래의 데이터를 저장함.
            * size :
            * color :
            * material :
            * */
            ClothingProduct clothing = new ClothingProduct("T-Shirt", 19.99, "FashionBrand", 100, "M", "Cotton", "Blue");
            em.persist(clothing);

            /*
            * 식품은 아래의 데이터를 저장함.
            * expirationDate; // 유통기한
            * isOrganic; // 유기농 여부
            * storageInstruction; // 보관 방법
            * */
            FoodProduct food = new FoodProduct("Milk", 2.99, "FoodBrand", 200, LocalDate.now().plusDays(7), true, "Refrigerate at 4°C");
            em.persist(food);

            em.flush();
            em.clear();

            // 모든 상품 조회
            System.out.println("모든 상품 조회:");
            em.createQuery("SELECT p FROM Product p", Product.class)
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
