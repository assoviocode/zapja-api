package com.assovio.zapja.zapjaapi.domain.service;

import com.assovio.zapja.zapjaapi.domain.dao.CampoCustomizadoDAO;
import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

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

    public List<CampoCustomizado> getObrigatorios() {
        return this.dao.findAllObrigatorios();
    }

    public List<CampoCustomizado> getByFilters(String rotulo, Boolean ativo, Boolean obrigatorio, Long tipoCampoCustomizadoId) {
        return this.dao.findByFilters(rotulo, ativo, obrigatorio, tipoCampoCustomizadoId);
    }

}
