DROP DATABASE IF EXISTS JPA_LECTURE;
CREATE DATABASE  JPA_LECTURE;

GRANT ALL PRIVILEGES ON JPA_LECTURE.* TO 'gorilla'@'%';

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;


CREATE TABLE users
(
    user_id       BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 기본 키, 자동 증가
    username      VARCHAR(100) NOT NULL UNIQUE,       -- 사용자 이름, NOT NULL, 유니크 제약조건
    email         VARCHAR(100) NOT NULL,              -- 이메일, NOT NULL
    password_hash VARCHAR(255) NOT NULL,              -- 해시된 비밀번호, NOT NULL
    birth_date    DATE,                               -- 생년월일
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP, -- 계정 생성일시, 기본값은 현재 시간
    role_id       VARCHAR(20)  NOT NULL               -- 역할, NOT NULL (Enum으로 저장)
);


CREATE TABLE products
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(255),
    price_amount         DECIMAL(10, 2),
    price_currency       VARCHAR(10),
    manufacturer_name    VARCHAR(255),
    manufacturer_country VARCHAR(255)
);


-- section 02
INSERT INTO users (user_id, username, email, password_hash, birth_date, created_at, role_id)
VALUES (1, '앨리스', 'alice@example.com', 'hashed_password_1', '1990-01-15', NOW(), 'STUDENT'),
       (2, '밥', 'bob@example.com', 'hashed_password_2', '1992-05-23', NOW(), 'INSTRUCTOR'),
       (3, '찰리', 'charlie@example.com', 'hashed_password_3', '1988-10-11', NOW(), 'ADMIN'),
       (4, '데이비드', 'david@example.com', 'hashed_password_4', '1995-03-30', NOW(), 'STUDENT'),
       (5, '이브', 'eve@example.com', 'hashed_password_5', '1993-07-22', NOW(), 'INSTRUCTOR'),
       (6, '프랭크', 'frank@example.com', 'hashed_password_6', '1991-12-05', NOW(), 'ADMIN'),
       (7, '그레이스', 'grace@example.com', 'hashed_password_7', '1989-11-16', NOW(), 'STUDENT'),
       (8, '하이디', 'heidi@example.com', 'hashed_password_8', '1994-08-02', NOW(), 'INSTRUCTOR'),
       (9, '아이반', 'ivan@example.com', 'hashed_password_9', '1996-04-25', NOW(), 'ADMIN'),
       (10, '주디', 'judy@example.com', 'hashed_password_10', '1990-09-09', NOW(), 'STUDENT');


-- section 03
INSERT INTO products (name, price_amount, price_currency, manufacturer_name, manufacturer_country)
VALUES ('스마트폰', 799.99, 'USD', '삼성전자', '대한민국'),
       ('노트북', 1299.99, 'USD', 'LG전자', '대한민국'),
       ('태블릿', 499.99, 'USD', '애플', '미국'),
       ('스마트워치', 199.99, 'USD', '구글', '미국'),
       ('헤드폰', 149.99, 'USD', '소니', '일본'),
       ('게임 콘솔', 499.99, 'USD', '마이크로소프트', '미국'),
       ('웨어러블 기기', 249.99, 'USD', '핏빗', '미국'),
       ('외장 하드디스크', 89.99, 'USD', '씨게이트', '미국'),
       ('프린터', 149.99, 'USD', 'HP', '미국'),
       ('모니터', 299.99, 'USD', '델', '미국');
