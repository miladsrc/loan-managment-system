package logic.service;

import base.service.BaseService;
import domain.Student;

public interface StudentService extends BaseService<Student, Long> {

    Student getStudentByNationalCodeAndPassword(String nationalCode, String password);
}
