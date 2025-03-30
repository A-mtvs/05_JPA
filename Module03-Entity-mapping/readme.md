# JPA 연관관계 매핑 수업: Customer, Order, Delivery
>  이 수업에서는 JPA(Java Persistence API)를 사용하여 엔티티 간의 연관관계를 매핑하는 방법을 학습한다. <br>
> Customer (1:N) Order (1:1) Delivery라는 도메인을 통해 다음과 같은 내용을 다룬다<br>


## 도메인 설계
### Customer (고객):
   - 한 명의 고객은 여러 주문을 가질 수 있음 (1:N 관계).
   - 고객 정보: 고객 ID (customer_id), 이름 (name).

### Order (주문):
  - 각 주문은 하나의 고객에 속하며, 하나의 배송 정보를 가짐 (1:N 및 1:1 관계).
  - 주문 정보: 주문 ID (order_id), 고객 참조, 배송 참조, 주문 날짜 (order_date).

### Delivery (배송):
  - 각 배송은 하나의 주문에 속함 (1:1 관계).
  - 배송 정보: 배송 ID (delivery_id), 주문 참조, 주소 (address), 상태 (status).


## 설계 의도
1. Customer와 Order의 1:N 관계
    - 목적 : 고객 한 명이 여러 주문을 생성할 수 있는 현실 세계의 관계를 반영.
    - 고객별 주문 목록을 쉽게 조회하고 관리하기 위해 설계.설계 의도
   
2. Customer와 Order의 1:N 관계
    - 목적 : 고객 한 명이 여러 주문을 생성할 수 있는 현실 세계의 관계를 반영.
    - 고객별 주문 목록을 쉽게 조회하고 관리하기 위해 설계.
   
3. Order와 Delivery의 1:1 관계
    - 목적 : 주문과 배송 정보를 별도의 엔티티로 분리하여 책임 분리(Single Responsibility Principle)를 준수.
    - 하나의 주문은 하나의 배송과 연결되며, 배송 상태를 독립적으로 관리.
