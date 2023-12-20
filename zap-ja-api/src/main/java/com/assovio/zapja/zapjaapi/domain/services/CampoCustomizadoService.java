package com.assovio.zapja.zapjaapi.domain.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.CampoCustomizadoDAO;
import com.assovio.zapja.zapjaapi.domain.models.CampoCustomizado;

@Service
public class CampoCustomizadoService {

    @Autowired
    private CampoCustomizadoDAO dao;

    public List<CampoCustomizado> getAll() {
        return (List<CampoCustomizado>) this.dao.findAll();
    }

    public CampoCustomizado getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public CampoCustomizado save(CampoCustomizado entity) {
        return this.dao.save(entity);
    }

    public void delete(CampoCustomizado entity) {
        this.dao.delete(entity);
    }

    public void deleteLogical(CampoCustomizado entity) {
        entity.setDeletedAt(OffsetDateTime.now());
        this.save(entity);
    }

    public Page<CampoCustomizado> getByFilters(String rotulo, Long tipoCampoCustomizadoId, Pageable pageable) {
        return this.dao.findByFilters(rotulo, tipoCampoCustomizadoId, pageable);
    }

}
