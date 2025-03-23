# 객체지향과 JPA의 시작 - 왜 우리는 JPA를 쓰는가?


## 📌 JPA의 필요성: 객체와 RDB의 패러다임 불일치 해결

### 1. 객체와 RDB의 차이점
자바와 같은 객체 지향 프로그래밍 언어는 객체(클래스, 상속, 연관 관계 등)를 중심으로 동작하지만, 관계형 데이터베이스(RDBMS)는 테이블(행과 열)로 데이터를 저장한다. 이로 인해 객체와 RDB 간의 패러다임 불일치 문제가 발생한다.

- 예제: 자바 객체 모델
    ```java
    class User {
        private Long id;
        private String username;
        private String email;
        private List<Order> orders; // 1:N 관계
    }
    ```

- 예제: 관계형 데이터베이스 테이블
    ```sql
    CREATE TABLE users (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE
    );

    CREATE TABLE orders (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT,
        order_date DATETIME,
        FOREIGN KEY (user_id) REFERENCES users(id)
    );
    ```
> 위 예제에서 자바의 User 객체는 orders라는 리스트를 통해 주문과 1:N 관계를 표현하지만, <b>RDB에서는 이를 별도의 orders 테이블과 외래 키로 관리</b>해야 한다.<br>
> 이런 구조적 차이로 인해 매핑 작업이 복잡해지게 된다.



### 🔍 2. 기존 기술(JDBC, MyBatis)와 패러다임 불일치 문제
과거에는 JDBC나 MyBatis 같은 기술을 주로 사용해 데이터베이스와 자바 애플리케이션을 연결했다. <br>
하지만 이러한 기술들은 패러다임 불일치로 인해 몇 가지 문제를 안고 있었다.

#### 🔧 JDBC의 문제
JDBC는 SQL 쿼리를 직접 작성해 데이터베이스와 상호작용한다.
- 문제점:
  - 객체와 테이블 간의 매핑을 수동으로 처리해야 함. 
    - 예를 들어, `ResultSet`에서 데이터를 꺼내 자바 객체에 일일이 매핑.
  - 반복적인 보일러플레이트 코드(연결, 쿼리 실행, 결과 매핑)가 증가.
  - 객체의 연관 관계(예: `User`와 `Order`의 1:N)를 처리하려면 여러 SQL 쿼리를 작성하고 조인 작업을 직접 관리해야 함.

#### 🔧 MyBatis의 문제
MyBatis는 SQL 매핑을 XML이나 어노테이션으로 간소화했지만, 여전히 SQL 중심 접근 방식을 취하게 된다.

- 문제점:
  - 객체 지향적 설계(상속, 다형성, 연관 관계)를 충분히 반영하기 어려움.
    - xml에 class의 정의 내용을 개별적으로 다 구현하고 하나의 데이터 셋으로 표현해야됨.
  - 복잡한 연관 관계를 다룰 때 SQL 쿼리가 점점 복잡해지고, 개발자가 이를 모두 관리해야 함.
    - 결국 모든 SQL을 개발자가 직접 작성해야하는 근본적인 문제는 해결되지 않음.

#### 🔧 공통 문제: 패러다임 불일치
- 데이터 탐색: 객체는 참조(user.getOrders())로 쉽게 연관 데이터를 탐색하지만, RDB에서는 조인 쿼리나 별도의 SELECT 쿼리가 필요.
- 상속: 객체의 상속 구조를 테이블로 표현하기 어려움
  - (예: Person과 Student의 상속 관계를 단일 테이블로? 별도 테이블로?)
- 코드 중복: 반복적인 매핑 로직으로 인해 생산성과 유지보수성이 떨어짐.

> 이러한 문제들로 인해 개발자는 비즈니스 로직에 집중하기보다 데이터베이스와 객체 간의 변환에 많은 시간을 소비하게 되었다.


### 💡 3 JPA의 등장과 해결 방안
JPA는 객체와 RDB 간의 패러다임 불일치를 해결하기 위해 등장했다. JPA는 ORM 프레임워크로, 자바 객체와 데이터베이스 테이블을 자동으로 매핑하며 다음과 같은 방식으로 문제를 해결한다.

- 해결 1: 자동화된 객체-테이블 매핑
  - @Entity, @Table, @Column 같은 어노테이션으로 자바 클래스를 데이터베이스 테이블에 매핑.
  - JPA는 이를 기반으로 users 테이블과 orders 테이블 간의 관계를 자동으로 관리.
  ```java
  @Entity
  @Table(name = "users")
  public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="username")
    private String username;
    private String email;

  }
  ```

-  해결 2: 객체 지향적 데이터 탐색
   - 연관 관계(@OneToMany, @ManyToOne)를 정의하면, user.getOrders() 같은 방식으로 객체 그래프를 탐색 가능.
   - 효과: 조인 쿼리나 추가 SELECT 없이도 연관 데이터를 쉽게 조회.
   ```java
      @OneToMany(mappedBy = "user")
      private List<Order> orders;
   ```

- 해결 3: 상속 처리
  - @Inheritance 어노테이션으로 상속 구조를 테이블에 반영(단일 테이블, 조인 테이블 등 전략 선택 가능).
  - 예제: Person과 Student의 상속 관계를 자동 매핑.

  ```java
      @Entity
      @Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략 
      // PERSON 클래스의 모든 자식 클래스가 동일한 테이블에 저장됨
      @DiscriminatorColumn(name = "person_type") // 구분 컬럼
      public class Person {
          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private Long id;
          private String name;
        }
  
      @Entity
      @DiscriminatorValue("STUDENT") // Student 구분 값
      public class Student extends Person {
       private String studentId;
      }
  ```

- 해결 4: 생산성과 유지보수성 향상
  - 반복적인 SQL 작성과 매핑 코드를 제거하고, 영속성 컨텍스트(Persistence Context)를 통해 객체 상태를 관리.
  - 효과: 데이터베이스 스키마 변경 시 엔티티 클래스만 수정하면 JPA가 나머지를 처리.

- 해결 5: 트랜잭션과 성능 최적화
  - 영속성 컨텍스트의 1차 캐시와 지연 로딩(Lazy Loading)을 통해 불필요한 쿼리를 줄이고 성능을 개선.


----


<br>
<br>
<br>
<br>




## 📌 2. ORM(Object-Relational Mapping) 개념 이해
### 1. ORM의 핵심 개념
| 개념                | 설명                                |
|-------------------|-------------------------------------|
| **Entity**        | 데이터베이스 테이블과 매핑되는 클래스      |
| **Primary Key**   | 엔티티의 기본 키 (Primary Key)          |
| **Persistence Context** | 엔티티를 관리하는 영속성 컨텍스트      |
| **JPQL**          | 객체를 대상으로 하는 쿼리 언어          |

### 2. ORM을 사용한 객체와 RDB 매핑 다이어그램
```plaintext
class User {
        +Long id
        +String username
        +String email
}

table users {
        +BIGINT id
        +VARCHAR username
        +VARCHAR email
    }

User --> users : ORM 매핑
```
### 3. 주요 기능
- **객체-관계 매핑**: 자바 객체와 데이터베이스 테이블 간의 매핑을 정의할 수 있다.
  - 즉 RDBMS의 테이블을 java에서 매핑하여 사용할 수 있게됨
- **데이터베이스 작업**: CRUD(Create, Read, Update, Delete) 작업을 손쉽게 수행할 수 있다.
  - 반복적인 쿼리 개선을 통한 비즈니스 로직에 집중을 할 수 있도록 지원함.
- **쿼리 언어**: JPQL(Java Persistence Query Language)을 사용하여 객체 지향 쿼리를 작성할 수 있다.
  - 테이블간 많은 join 혹은 group이 필요한 경우 직접 쿼리를 작성할 수 있다.
- **트랜잭션 관리**: JPA는 트랜잭션 관리를 지원하여 데이터의 일관성을 유지한다.
  - 어노테이션을 통해 트랜잭션을 효율적으로 관리하는 것이 가능하다.

------

<br>
<br>
<br>
<br>

## 📌 3. Hibernate와 JPA의 차이
JPA는 Java Persistence API로, 자바에서 객체-관계 매핑(ORM)을 통해 데이터베이스와의 상호작용을 단순화하는 표준이다. <br>
JPA는 데이터베이스와 상호작용을 위한 표준 인터페이스로, 이를 구현한 여러 구현체가 존재하며, 이들 구현체는 JPA의 규격을 따른다.

우리는 이 중에서 Hibernate를 사용하여 학습할 것이며, Hibernate는 JPA의 가장 널리 사용되는 구현체 중 하나로, 강력한 기능과 유연성을 제공한다.<br>
시장에서도 Hibernate는 많은 개발자들에 의해 선호되며, 다양한 프로젝트에서 사용되고 있다.

- 구현체 종류
  - EclipseLink : Oracle에서 개발한 JPA 구현체
  - Open JPA : Apache에서 개발한 JPA 구현체
  - DataNucleus : JPA와 JDO(java DTA Objects)도 지원하는 구현체


### 1. JPA의 표준화와 Hibernate의 구현
JPA는 ORM을 표준화하여 개발자가 특정 벤더에 종속되지 않도록 설계되었다. <br>
Hibernate는 JPA의 구현체 중 하나로, JPA 스펙을 준수하면서도 자체적인 기능을 추가로 제공한다.

-  예제: JPA 기반 코드
  ```java
  @Entity
  public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
  }
  ```
> 위 코드는 JPA 표준 어노테이션을 사용하며, Hibernate가 이를 실제로 실행하게 된다.

### 2. 차이로 인한 장단점
- JPA의 장점
  - 호환성: Hibernate 외에 EclipseLink, OpenJPA 등 다른 구현체로 쉽게 전환 가능.
  - 표준화: 자바 커뮤니티의 공식 표준을 따르므로 학습 및 유지보수가 용이.
- Hibernate의 장점
  - 확장성: JPA에 없는 고급 기능(예: 2차 캐시, HQL, 다중 테넌시) 사용 가능.
    - 2차 캐시 : 애플리케이션 전역에서 자주 반복되는 데이터를 캐싱하여 DB에 접근하지 않고 반환할 수 있도록 하는 매커니즘.
    - HQL(hibernate Query Language) : 객체지향 쿼리 언어로, SQL과 유사하지만 테이블이 아닌 Hibernate 엔티티 객체를 대상으로 한다.
    - 다중 테넌시 : 여러 고객이 동일한 애플리케이션을 사용해도 각자의 데이터를 이용할 수 있도록 분산하여 안전하게 관리하는 기능.
  - 성능 최적화: Hibernate 고유의 설정으로 세부적인 튜닝 가능.
- 공통 문제
  - JPA만 사용하면 구현체 고유의 강력한 기능을 활용하기 어려움.
  - Hibernate 단독 사용 시 표준에서 벗어나 다른 구현체로의 전환이 어려워질 수 있음.

### 3. Hibernate와 JPA의 정리
- JPA의 역할
  - ORM의 표준을 정의하여 일관된 개발 경험 제공.
  - 개발자는 @Entity, EntityManager 같은 표준 API로 작업.
- Hibernate의 역할
  - JPA 스펙을 구현하여 실제 데이터베이스 연동 처리.
  - 추가 기능으로 JPA의 한계를 보완.
- 결과
  - JPA로 표준화된 코드를 유지하면서, 필요 시 Hibernate의 고유 기능을 활용 가능.
- 해결 예시: 캐싱
  - JPA는 기본 캐싱을 제공하지만, Hibernate는 EHCache나 Infinispan 같은 2차 캐시를 통해 더 강력한 성능 최적화 가능.


| 비교 항목          | JPA                          | Hibernate                             |
|------------------|-----------------------------|---------------------------------------|
| **정의**         | 자바의 ORM 표준                | JPA의 구현체 (구체적인 기능 포함)       |
| **API 제공**     | EntityManager, Query 등 제공 | JPA 기능 + 추가 기능 제공              |
| **SQL 자동 생성**| 지원                         | 지원 (더 많은 최적화 기능 포함)        |
| **벤더 종속성**  | 없음 (EclipseLink, Hibernate 등 선택 가능) | Hibernate에 종속됨                    |


### 🎯 JPA의 장점
- **생산성 향상**: 반복적인 SQL 쿼리 작성에서 벗어나 객체 지향적으로 개발할 수 있어 생산성이 높아진다.
- **유지보수 용이**: 데이터베이스 구조가 변경되더라도 객체 모델만 수정하면 되므로 유지보수가 용이하다.
- **표준화**: JPA는 자바 EE(Enterprise Edition)와 함께 제공되는 표준 API로, 다양한 JPA 구현체(Hibernate, EclipseLink 등)를 통해 사용할 수 있다.




-----

<br>
<br>
<br>



## 📌 4. 환경 설정: Spring Boot에서 JPA 사용하기
Spring Boot에서는 JPA를 쉽게 설정할 수 있도록 spring-boot-starter-data-jpa를 제공한다.
- [jakarta persistence api](https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api/3.1.0)
- [hibernate](https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core/6.6.10.Final)
```gradle
dependencies {
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation group: 'org.hibernate.orm', name: 'hibernate-core', version: '6.6.10.Final'
    // https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api
    implementation group: 'jakarta.persistence', name: 'jakarta.persistence-api', version: '3.1.0'

    testImplementation platform('org.junit:junit-bom:5.10.0')
    testImplementation 'org.junit.jupiter:junit-jupiter'
}
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb 
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

