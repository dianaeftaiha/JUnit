package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void getsAllStudents() {
        studentService.getAllStudents();

        verify(studentRepository).findAll();
    }

    @Test
    void addsStudent() {
        String email = "diana@gmail.com";

        Student student = new Student(
                "Diana",
                email,
                Gender.FEMALE
        );

        studentService.addStudent(student);

        verify(studentRepository).selectExistsEmail(student.getEmail());

        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();

        assertSame(student, capturedStudent);
    }

    @Test
    void throwsExceptionWhenEmailIsTaken() {
        String email = "diana@gmail.com";

        Student student = new Student(
                "Diana",
                email,
                Gender.FEMALE
        );

        given(studentRepository.selectExistsEmail(student.getEmail()))
                .willReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> studentService.addStudent(student));
        assertEquals(exception.getMessage(), "Email " + email + " is taken");
    }

    @Test
    void deletesStudent() {
    }
}