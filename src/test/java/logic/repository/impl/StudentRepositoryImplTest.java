package logic.repository.impl;

import domain.Student;
import logic.service.StudentService;
import org.junit.jupiter.api.Test;
import util.ApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryImplTest {

    @org.junit.jupiter.api.Test
    void getEntityClass() {
    }

    @org.junit.jupiter.api.Test
    void findByNationalCodeAndPassword() {
    }


    @Test
    public void test() {
        StudentService studentService = ApplicationContext.getStudentService();

        String nationalCode = "1";
        String password = "1";

        Student student = studentService.getStudentByNationalCodeAndPassword(nationalCode, password);

        if (student != null) {
            System.out.println("Student found:");
            System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("Student Number: " + student.getStudentNumber());
        } else {
            System.out.println("Student not found. Please check the provided credentials.");
        }
    }

}
