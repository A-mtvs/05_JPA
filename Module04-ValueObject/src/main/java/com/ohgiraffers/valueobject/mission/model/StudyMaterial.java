package com.ohgiraffers.valueobject.mission.model;

import jakarta.persistence.*;

import java.util.Objects;

/*
 * 📌 StudyMaterial 값 객체
 * - Lesson에 포함된 개별 학습 자료(PDF, 비디오, 텍스트 등)를 나타냅니다.
 * - @Embeddable로 설계하여 Lesson의 일부로 관리되며, 독립적인 생명주기를 가지지 않음.
 */
@Embeddable
public class StudyMaterial {
    @Column(name = "material_type")
    private String materialType; // PDF, VIDEO, TEXT

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;

    protected StudyMaterial() {}

    public StudyMaterial(String materialType, String url, String description) {
        if (materialType == null || materialType.trim().isEmpty()) {
            throw new IllegalArgumentException("자료 유형은 필수입니다.");
        }
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("자료 URL은 필수입니다.");
        }
        this.materialType = materialType;
        this.url = url;
        this.description = description;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    /*
     * 📌 equals와 hashCode 재정의
     * - materialType과 url을 기준으로 동등성 비교.
     * - 값 객체로서 동일한 데이터를 가진 객체는 동일한 것으로 간주.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyMaterial that = (StudyMaterial) o;
        return Objects.equals(materialType, that.materialType) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialType, url);
    }

    @Override
    public String toString() {
        return materialType + " (" + url + ", " + description + ")";
    }
}