package logic.repository;

import base.repository.BaseRepository;
import domain.Student;

import javax.management.Query;
import java.util.Optional;

public interface StudentRepository extends BaseRepository<Student, Long> {

    Optional<Student> findByNationalCodeAndPassword(String nationalCode, String password);
}
