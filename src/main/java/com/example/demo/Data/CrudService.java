package com.example.demo.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public abstract class CrudService<T, ID> {

    protected abstract JpaRepository<T, ID> repo();

    public T save(T entity) {
        return repo().save(entity);
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return repo().findById(id);
    }

    public void delete(T entity) {
        repo().delete(entity);
    }
}
