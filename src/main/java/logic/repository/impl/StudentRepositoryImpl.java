package logic.repository.impl;


import base.repository.BaseRepositoryImpl;
import domain.Student;
import logic.repository.StudentRepository;
import org.hibernate.Session;

public class StudentRepositoryImpl extends BaseRepositoryImpl<Student, Long> implements StudentRepository {
    public StudentRepositoryImpl(Session session) {
        super ( session );
    }

    @Override
    public Class<Student> getEntityClass() {
        return null;
    }
}
