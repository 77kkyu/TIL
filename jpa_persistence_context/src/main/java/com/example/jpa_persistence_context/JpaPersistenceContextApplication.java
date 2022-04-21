package com.example.jpa_persistence_context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class JpaPersistenceContextApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaPersistenceContextApplication.class, args);
    }

}
