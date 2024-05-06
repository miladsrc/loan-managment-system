package logic.repository.impl;


import base.repository.BaseRepositoryImpl;
import domain.Student;
import logic.repository.StudentRepository;
import logic.service.StudentService;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.Random;

public class StudentRepositoryImpl extends BaseRepositoryImpl<Student, Long> implements StudentRepository {
    public StudentRepositoryImpl(Session session) {
        super ( session );
    }

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }


    @Override
    public Student findByNationalCodeAndPassword(String nationalCode, String password) {

        String hql = "FROM Student s WHERE s.nationalCode = :nationalcode AND s.password = :password";
        Query<Student> query = session.createQuery(hql, Student.class);
        query.setParameter("nationalcode", nationalCode);
        query.setParameter("password", password);
        return query.uniqueResult();

    }


}
