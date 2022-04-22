package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void checksEmailExists() {
        String email = "diana@gmail.com";

        Student student = new Student(
            "Diana",
                email,
            Gender.FEMALE
        );

        underTest.save(student);

        boolean emailExists = underTest.selectExistsEmail(email);

        assertTrue(emailExists);
    }

    @Test
    void checksEmailDoesNotExist() {
        String email = "diana@gmail.com";

        boolean emailExists = underTest.selectExistsEmail(email);

        assertFalse(emailExists);
    }
}