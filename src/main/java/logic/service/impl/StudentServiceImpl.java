package logic.service.impl;

import base.service.BaseServiceImpl;
import domain.Student;
import logic.repository.StudentRepository;
import logic.service.StudentService;

public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository>
implements StudentService {
    public StudentServiceImpl(StudentRepository repository) {
        super ( repository );
    }
}
