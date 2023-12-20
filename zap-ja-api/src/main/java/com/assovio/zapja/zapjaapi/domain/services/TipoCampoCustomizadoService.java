package com.assovio.zapja.zapjaapi.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.TipoCampoCustomizadoDAO;
import com.assovio.zapja.zapjaapi.domain.models.TipoCampoCustomizado;

@Service
public class TipoCampoCustomizadoService {

    @Autowired
    private TipoCampoCustomizadoDAO dao;

    public List<TipoCampoCustomizado> getAll() {
        return (List<TipoCampoCustomizado>) this.dao.findAll();
    }

    public TipoCampoCustomizado getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public TipoCampoCustomizado save(TipoCampoCustomizado entity) {
        return this.dao.save(entity);
    }

    public void delete(TipoCampoCustomizado entity) {
        this.dao.delete(entity);
    }

    public Page<TipoCampoCustomizado> getByFilters(String nome, String mascara, Pageable pageable) {
        return this.dao.findByFilters(nome, mascara, pageable);
    }

}
