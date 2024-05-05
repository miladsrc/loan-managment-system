package logic.service.impl;

import base.service.BaseServiceImpl;
import domain.Student;
import logic.repository.StudentRepository;
import logic.service.StudentService;

import java.util.Optional;

public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository>
implements StudentService {
    public StudentServiceImpl(StudentRepository repository) {
        super ( repository );
    }


    @Override
    public Optional<Student> getStudentByNationalCodeAndPassword(String nationalCode, String password){
        return repository.findByNationalCodeAndPassword(nationalCode, password);
    }
}
