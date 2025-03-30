package com.ohgiraffers.valueobject.mission;


import com.ohgiraffers.valueobject.mission.model.Lesson;
import com.ohgiraffers.valueobject.mission.model.StudyMaterial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Optional;

/*
 * 📌 Application 클래스
 * - Lesson과 StudyMaterial 간의 관계를 @ElementCollection으로 설정하여,
 *   값 타입으로 관리했을 때의 장점을 보여줍니다.
 * - StudyMaterial의 추가, 제거, 조회 기능을 테스트합니다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lesson");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {
            // Lesson 생성
            Lesson lesson = new Lesson( "Introduction to Java", "Basic Java concepts", "http://example.com/java-video.mp4");
            em.persist(lesson);
            System.out.println("Lesson 생성 후: " + lesson);

            // StudyMaterial 추가
            StudyMaterial pdfMaterial = new StudyMaterial("PDF", "http://example.com/java.pdf", "Java Basics PDF");
            StudyMaterial videoMaterial = new StudyMaterial("VIDEO", "http://example.com/java-video.mp4", "Java Intro Video");
            lesson.addStudyMaterial(pdfMaterial);
            lesson.addStudyMaterial(videoMaterial);

            em.flush();

            System.out.println("StudyMaterial 추가 후: " + lesson.getStudyMaterials());

            // 중복 StudyMaterial 추가 시도
            try {
                StudyMaterial duplicateMaterial = new StudyMaterial("PDF", "http://example.com/java.pdf", "Duplicate PDF");
                lesson.addStudyMaterial(duplicateMaterial);
            } catch (IllegalArgumentException e) {
                System.out.println("중복 추가 시도 결과: " + e.getMessage());
            }

            // 특정 유형의 StudyMaterial 조회
            System.out.println("PDF 자료 조회: " + lesson.findStudyMaterialsByType("PDF"));

            // 특정 URL의 StudyMaterial 조회
            // Optional  null 값으로 인한 NullPointerException을 방지하기 위해 사용
            Optional<StudyMaterial> foundMaterial = lesson.findStudyMaterialByUrl("http://example.com/java-video.mp4");
            System.out.println("URL로 조회한 자료: " + (foundMaterial.isPresent() ? foundMaterial.get() : "없음"));

            // StudyMaterial 제거
            lesson.removeStudyMaterial(new StudyMaterial("PDF", "http://example.com/java.pdf", "Any Description"));
            em.flush();
            System.out.println("PDF 자료 제거 후: " + lesson.getStudyMaterials());

            // Lesson 삭제 (StudyMaterial도 함께 삭제됨)
            em.remove(lesson);
            em.flush();
            System.out.println("Lesson 삭제 완료");

            em.getTransaction().commit();

        } catch (IllegalArgumentException e) {
            System.err.println("유효하지 않은 값입니다: " + e.getMessage());
            em.getTransaction().rollback();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}