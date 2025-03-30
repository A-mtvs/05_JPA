# 미션 개요: 값 타입을 활용한 LMS 기능 개선

## 미션 목표
LMS 시스템에서 특정 도메인 객체를 값 타입(@Embeddable)으로 설계하여, @OneToMany 관계로 발생하는 문제(불필요한 엔티티 관리, 동등성 비교 어려움, 컬렉션 조작 번거로움, 양방향 연관관계 복잡성)를 해결하고, 값 타입의 장점을 활용한 기능을 구현합니다.

### 미션 배경
현재 LMS 시스템에서 Course와 Lesson은 @OneToMany 관계로 설계되어 있습니다.<br> 
그러나 Lesson 내에 포함된 **학습 자료(StudyMaterial)**와 같은 데이터는 Lesson에 종속적인 데이터로, 독립적인 엔티티로 관리할 필요가 없습니다.<br>
이를 값 타입으로 설계하여 도메인적으로 더 적합한 구조를 만들고, 값 타입의 장점을 활용한 기능을 구현합니다.

### 새로운 도메인 추가: 학습 자료(StudyMaterial)
- 학습 자료(StudyMaterial): Lesson에 포함된 개별 학습 자료(예: PDF 파일, 비디오 링크, 텍스트 자료 등)를 의미

- 속성:
  - materialType: 자료 유형 (예: PDF, VIDEO, TEXT)
  - url: 자료의 URL 또는 경로
  - description: 자료에 대한 설명

- 특징:
  - Lesson에 종속적이며, 독립적인 생명주기를 가지지 않음.
  - 값 타입으로 설계하여 불변성을 보장하고, Lesson의 일부로 관리.

```mysql
CREATE TABLE study_materials (
    lesson_id INT NOT NULL,
    material_type VARCHAR(50) NOT NULL,
    url VARCHAR(500) NOT NULL,
    description TEXT,
    PRIMARY KEY (lesson_id, material_type, url),
    FOREIGN KEY (lesson_id) REFERENCES lessons(lesson_id) ON DELETE CASCADE
) COMMENT='강의별 학습 자료 정보를 저장하는 테이블';

```