package com.ohgiraffers.valueobject.mission.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long id;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /*
     * 📌 @ElementCollection
     * - StudyMaterial을 값 객체로 관리하며, 별도의 테이블(study_materials)에 매핑.
     * - 양방향 연관관계를 관리할 필요가 없으며, StudyMaterial은 Lesson의 일부로 동작.
     */
    @ElementCollection
    @CollectionTable(name = "study_materials", joinColumns = @JoinColumn(name = "lesson_id"))
    private List<StudyMaterial> studyMaterials = new ArrayList<>();

    protected Lesson() {}

    public Lesson( String title, String content, String videoUrl) {
        this.title = title;
        this.content = content;
        this.videoUrl = videoUrl;
        this.createdAt = LocalDateTime.now();
    }

    /*
     * 📌 StudyMaterial 추가 메서드
     * - 중복 추가를 방지하기 위해 materialType과 url을 기준으로 확인.
     */
    public void addStudyMaterial(StudyMaterial material) {
        // anyMatch 주어진 조건을 만족하는 요소가 스트림에 하나라도 존재하는지를 확인
        if (studyMaterials.stream().anyMatch(m -> m.equals(material))) {
            throw new IllegalArgumentException("이미 존재하는 학습 자료입니다: " + material);
        }
        this.studyMaterials.add(material);
    }

    /*
     * 📌 StudyMaterial 제거 메서드
     * - 값 객체의 equals 메서드를 사용하여 materialType과 url을 기준으로 제거.
     */
    public void removeStudyMaterial(StudyMaterial material) {
        // .remveif 주어진 조건을 만족하는 요소를 컬렉션에서 제거
        this.studyMaterials.removeIf(m -> m.equals(material));
    }

    /*
     * 📌 특정 유형의 StudyMaterial 조회 메서드
     * - materialType을 기준으로 필터링.
     */
    public List<StudyMaterial> findStudyMaterialsByType(String materialType) {
        return studyMaterials.stream()
                // .filter 주어진 조건에 맞는 요소만을 포함하는 새로운 스트림을 생성
                .filter(m -> m.getMaterialType().equals(materialType))
                .toList();
    }

    /*
     * 📌 특정 URL의 StudyMaterial 조회 메서드
     * - url을 기준으로 조회.
     */
    public Optional<StudyMaterial> findStudyMaterialByUrl(String url) {
        return studyMaterials.stream()
                .filter(m -> m.getUrl().equals(url))
                .findFirst();
    }

    public List<StudyMaterial> getStudyMaterials() {
        return studyMaterials;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", createdAt=" + createdAt +
                ", studyMaterials=" + studyMaterials +
                '}';
    }
}