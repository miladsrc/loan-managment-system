package logic.repository.impl;


import base.repository.BaseRepositoryImpl;
import domain.Student;
import jakarta.persistence.Query;
import logic.repository.StudentRepository;
import logic.service.StudentService;
import org.hibernate.Session;

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
    public Optional<Student> findByNationalCodeAndPassword(String nationalCode, String password) {
        String hql = "FROM Student s WHERE s.nationalCode = :nationalCode AND s.password = :password";
        Query query = session.createQuery(hql, Student.class);
        query.setParameter("nationalCode", nationalCode);
        query.setParameter("password", password);

        return (Optional<Student>) query.getSingleResult();
    }

}
