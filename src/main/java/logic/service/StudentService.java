package logic.service;

import base.service.BaseService;
import domain.Student;

import java.util.Optional;

public interface StudentService extends BaseService<Student, Long> {

    Optional<Student> getStudentByNationalCodeAndPassword(String nationalCode, String password);
}
