package com.assovio.zapja.zapjaapi.domain.service;

import java.util.List;

public interface GenericService<T, ID> {

    public List<T> getAll();

    public T getById(ID id);

    public void saveAll(List<T> entitys);

    public T save(T entity);

    public void delete(T entity);

    public void deleteLogical(T entity);
}
