package org.base.service;

import jakarta.persistence.EntityTransaction;
import org.base.entity.BaseEntity;
import org.base.exception.NotFoundException;
import org.base.repository.BaseRepository;
import org.hibernate.HibernateError;
import org.hibernate.SessionFactory;
import org.util.SessionFactorySingleton;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseServiceImpl<T extends BaseEntity<ID>,
        ID extends Serializable,
        R extends BaseRepository<T, ID>>
        implements BaseService<T, ID> {

    protected final R repository;
    final SessionFactory sessionFactory;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
        sessionFactory = SessionFactorySingleton.getInstance ();
    }


    //METHOD
    @Override
    public T saveOrUpdate(T entity) {
        try {
            beginTransaction ();
            entity = repository.saveOrUpdate ( entity );
            commitTransaction ();
            return entity;
        } catch (HibernateError error) {
            System.out.println ( error.getMessage () );
            assert repository != null;
            rollbackTransaction ();
        } catch (Exception e) {
            System.out.println ( e.getMessage () );
        }
        return entity;
    }


    @Override
    public T findById(ID id) throws NotFoundException {
        try {
            beginTransaction ();
            Optional<T> optionalT = repository.findById ( id );
            if(optionalT.isPresent()) {
                T foundEntity = repository.findById ( id ).get();
                commitTransaction ();
                return foundEntity;
            }else {
                throw new NotFoundException (String.format("entity with id %s not found", id));
            }
        } catch (Exception e) {
            assert repository != null;
            rollbackTransaction ();
            throw new NotFoundException ( String.format ( "entity with %s not found", id ) );
        }
    }

    @Override
    public void delete(T t) {
        try {
            beginTransaction ();
            repository.delete ( t );
            commitTransaction ();
        } catch (Exception e) {
            assert repository != null;
            repository.rollbackTransaction ();
            System.out.println ( e.getMessage () );
        }
    }

    @Override
    public List<T> findAll() {
        try {
            beginTransaction ();
            List<T> t = repository.findAll ();
            commitTransaction ();
            return t;
        } catch (Exception e) {
            assert repository != null;
            repository.rollbackTransaction ();
            System.out.println ( e.getMessage () );
        }
        return null;
    }

    @Override
    public void deleteById(ID id) {
        try {
            beginTransaction ();
            repository.deleteById ( id );
            commitTransaction ();
            System.out.println ( "\ndeleted : " + id );
        } catch (Exception e) {
            assert repository != null;
            repository.rollbackTransaction ();
            System.out.println ( e.getMessage () );
        }
    }


    @Override
    public Optional<T> existsByUsernameAndPassword(String username, String password){
        try {
            beginTransaction();
            Optional<T> user = repository.existsByUsernameAndPassword(username, password);
            commitTransaction();
            return user;
        } catch (Exception e) {
            if (repository != null) {
                repository.rollbackTransaction();
            }
            System.out.println(e.getMessage());
            throw new RuntimeException("Error checking existence by username and password", e);
        }
    }


    @Override
    public Long countAll() {
        try {
            beginTransaction ();
            Long countedEntity = repository.countAll ();
            commitTransaction ();
            return countedEntity;
        } catch (Exception e) {
            assert repository != null;
            repository.rollbackTransaction ();
            System.out.println ( e.getMessage () );
        }
        return null;
    }

    @Override
    public void beginTransaction() {
        repository.beginTransaction ();
    }

    @Override
    public void commitTransaction() {
        repository.commitTransaction ();
    }

    @Override
    public void rollbackTransaction() {
        repository.rollbackTransaction ();
    }

    @Override
    public EntityTransaction getTransaction() {
        return null;
    }
}