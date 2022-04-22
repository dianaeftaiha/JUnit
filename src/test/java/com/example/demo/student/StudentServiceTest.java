package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void getsAllStudents() {
        underTest.getAllStudents();

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

        underTest.addStudent(student);

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

        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> underTest.addStudent(student));
        assertEquals(exception.getMessage(), "Email " + email + " is taken");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void deletesStudent() {
        given(studentRepository.existsById(any()))
                .willReturn(false);

        assertThrows(StudentNotFoundException.class, () -> underTest.deleteStudent(any(Long.class)));
        verify(studentRepository, never()).deleteById(any());
    }
}