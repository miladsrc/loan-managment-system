package org.base.repository;

import jakarta.persistence.EntityTransaction;
import org.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<ID>,ID extends Serializable> {

    T saveOrUpdate(T entity);

    Optional<T> findById(ID id);

    void delete(T entity);

    //added
    List<T> findAll();

    void deleteById(ID id);

    Long countAll();

    Optional<T> existsByUsernameAndPassword(String username, String password);

    //plus one --> we use them in service layer if we have to...
    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

    EntityTransaction getTransaction();
}
