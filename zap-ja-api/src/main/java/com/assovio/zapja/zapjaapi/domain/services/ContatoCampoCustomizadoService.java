package com.assovio.zapja.zapjaapi.domain.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.ContatoCampoCustomizadoDAO;
import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;

@Service
public class ContatoCampoCustomizadoService {

    @Autowired
    private ContatoCampoCustomizadoDAO dao;

    public List<ContatoCampoCustomizado> getAll() {
        return (List<ContatoCampoCustomizado>) this.dao.findAll();
    }

    public ContatoCampoCustomizado getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public ContatoCampoCustomizado save(ContatoCampoCustomizado entity) {
        return this.dao.save(entity);
    }

    public void delete(ContatoCampoCustomizado entity) {
        this.dao.delete(entity);
    }

    public void deleteLogical(ContatoCampoCustomizado entity) {
        entity.setDeletedAt(OffsetDateTime.now());
        this.save(entity);
    }

    public List<ContatoCampoCustomizado> getByFilters(Long contatoId, Long campoCustomizadoId) {
        return this.dao.findByFilters(contatoId, campoCustomizadoId);
    }

    public ContatoCampoCustomizado getByContatoAndCampoCustomizado(Long contatoId, Long campoCustomizadoId) {
        return this.dao.findFirstByContatoIdAndCampoCustomizadoId(contatoId, campoCustomizadoId);
    }

}
