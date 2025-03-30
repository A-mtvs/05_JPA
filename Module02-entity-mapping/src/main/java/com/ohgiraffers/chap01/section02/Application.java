package com.ohgiraffers.chap01.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();
        User user = em.getReference(User.class, 1);
        System.out.println(user);
    }
}
