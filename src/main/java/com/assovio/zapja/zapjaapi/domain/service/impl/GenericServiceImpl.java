package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.assovio.zapja.zapjaapi.domain.model.contracts.ILogicalDeletable;
import com.assovio.zapja.zapjaapi.domain.service.GenericService;

public class GenericServiceImpl<T, ID, DAO extends CrudRepository<T, ID>> implements GenericService<T, ID> {

    @Autowired
    protected DAO dao;

    public List<T> getAll() {
        return (List<T>) this.dao.findAll();
    }

    public T getById(ID id) {
        return this.dao.findById(id).orElse(null);
    }

    @Transactional
    public T save(T entity) {
        return this.dao.save(entity);
    }

    @Transactional
    public void delete(T entity) {
        this.dao.delete(entity);
    }

    @Transactional
    public void deleteLogical(T entity) {
        if (entity instanceof ILogicalDeletable) {
            ((ILogicalDeletable) entity).setDeletedAt(OffsetDateTime.now());
            this.dao.save(entity);
        } else {
            throw new UnsupportedOperationException("Entidade não suporta deleção lógica!");
        }
    }

    @Override
    public void saveAll(List<T> entitys) {
        this.dao.saveAll(entitys);
    }
}
