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


    @Override
    public Student getStudentByNationalCodeAndPassword(String nationalCode, String password){
        try {
            return repository.findByNationalCodeAndPassword(nationalCode, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
