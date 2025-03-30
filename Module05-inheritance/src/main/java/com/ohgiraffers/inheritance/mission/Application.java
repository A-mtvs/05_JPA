package com.ohgiraffers.inheritance.mission;

import com.ohgiraffers.inheritance.mission.model.Admin;
import com.ohgiraffers.inheritance.mission.model.Instructor;
import com.ohgiraffers.inheritance.mission.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-lesson");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student student = new Student("김학생", "student@email.com", "1234", "2025001");
        Instructor instructor = new Instructor("이강사", "inst@email.com", "abcd", "Java");
        Admin admin = new Admin("관리자", "admin@lms.com", "adminpass", "팀장", "운영팀");

        em.persist(student);
        em.persist(instructor);
        em.persist(admin);

        em.getTransaction().commit();

        System.out.println(student);
        System.out.println(instructor);
        System.out.println(admin);

        em.close();
        emf.close();
    }
}
