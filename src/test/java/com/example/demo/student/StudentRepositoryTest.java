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
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void testItChecksEmailExists() {
        String email = "diana@gmail.com";

        Student student = new Student(
            "Diana",
                email,
            Gender.FEMALE
        );

        studentRepository.save(student);

        boolean emailExists = studentRepository.selectExistsEmail(email);

        assertTrue(emailExists);
    }

    @Test
    void testItChecksEmailDoesNotExist() {
        String email = "diana@gmail.com";

        boolean emailExists = studentRepository.selectExistsEmail(email);

        assertFalse(emailExists);
    }
}