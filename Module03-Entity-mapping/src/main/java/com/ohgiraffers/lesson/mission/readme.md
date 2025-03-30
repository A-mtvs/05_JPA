# 미션 1: 간단한 온라인 쇼핑몰 모델링

## 목표
JPA의 `@ManyToOne`과 `@OneToMany` 연관관계 매핑을 이해하고 적용한다.

## 설명
간단한 온라인 쇼핑몰의 데이터 모델을 설계하고, 카테고리, 상품, 주문 간의 연관관계를 JPA 엔티티로 매핑한다.

## 엔티티 설계
* **Category**
    * id (PK)
    * name
* **Product**
    * id (PK)
    * name
    * price
    * category (FK, Category 엔티티와 `@ManyToOne` 관계)
* **Order**
    * id (PK)
    * orderDate
* **OrderItem**
    * id (PK)
    * product (FK, Product 엔티티와 `@ManyToOne` 관계)
    * order (FK, Order 엔티티와 `@ManyToOne` 관계)
    * quantity

## 연관관계 매핑
* `Category` 엔티티와 `Product` 엔티티 간의 `@OneToMany` 및 `@ManyToOne` 연관관계 매핑
* `Order` 엔티티와 `OrderItem` 엔티티 간의 `@OneToMany` 및 `@ManyToOne` 연관관계 매핑
* `OrderItem` 엔티티와 `Product` 엔티티 간의 `@ManyToOne` 연관관계 매핑

## 수행 방법
1.  위에서 정의된 엔티티 클래스를 JPA 어노테이션을 사용하여 구현합니다.
2.  각 엔티티 간의 연관관계를 `@ManyToOne`과 `@OneToMany` 어노테이션을 사용하여 매핑합니다.
3.  EntityManager를 사용하여 다음 기능을 구현하고 테스트합니다.
    * 특정 카테고리를 조회하고 해당 카테고리에 속한 모든 상품 목록을 출력합니다.
    * 특정 주문을 조회하고 해당 주문에 포함된 모든 주문 상품의 정보 (상품 이름, 수량 등)를 출력합니다.

## 추가 도전 과제
* 새로운 카테고리와 상품을 생성하고 데이터베이스에 저장하는 기능을 추가합니다.
* 특정 주문에 새로운 주문 상품을 추가하는 기능을 추가합니다.