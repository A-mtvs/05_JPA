-- 1. deliveries 테이블 제거
DROP TABLE IF EXISTS deliveries;

-- 2. orders 테이블 제거
DROP TABLE IF EXISTS orders;

-- 3. customers 테이블 제거
DROP TABLE IF EXISTS customers;


CREATE TABLE customers
(
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 고객 ID, 자동 증가 기본 키
    name        VARCHAR(100) NOT NULL              -- 고객 이름, 최대 100자, NOT NULL 제약
);

CREATE TABLE orders
(
    order_id    BIGINT AUTO_INCREMENT PRIMARY KEY,                                 -- 주문 ID, 자동 증가 기본 키
    customer_id BIGINT   NOT NULL,                                                 -- 고객 ID, 외래키, NOT NULL 제약
    order_date  DATETIME NOT NULL,                                                 -- 주문 날짜, NOT NULL 제약
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE -- 외래키 제약, 고객 삭제 시 주문도 삭제
);


CREATE TABLE deliveries
(
    delivery_id BIGINT AUTO_INCREMENT PRIMARY KEY,                        -- 배송 ID, 자동 증가 기본 키
    order_id    BIGINT       NOT NULL UNIQUE,                             -- 주문 ID, 외래키, NOT NULL 및 UNIQUE 제약 (1:1 관계)
    address     VARCHAR(255) NOT NULL,                                    -- 배송 주소, NOT NULL 제약
    status      VARCHAR(255) NOT NULL,                                    -- 배송 상태, NOT NULL 제약
    FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE -- 외래키 제약, 주문 삭제 시 배송도 삭제
);