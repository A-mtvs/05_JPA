package com.ohgiraffers.chap01.section03;

import com.ohgiraffers.chap01.section02.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("jpa-lecture");
        EntityManager em = emf.createEntityManager();
        Product user = em.getReference(Product.class, 1);
        System.out.println(user);
    }
}
