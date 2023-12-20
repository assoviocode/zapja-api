package com.assovio.zapja.zapjaapi.domain.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.TemplateWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.models.TemplateWhats;

@Service
public class TemplateWhatsService {

    @Autowired
    private TemplateWhatsDAO dao;

    public List<TemplateWhats> getAll() {
        return (List<TemplateWhats>) this.dao.findAll();
    }

    public TemplateWhats getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public TemplateWhats save(TemplateWhats entity) {
        return this.dao.save(entity);
    }

    public void delete(TemplateWhats entity) {
        this.dao.delete(entity);
    }

    public void deleteLogical(TemplateWhats entity) {
        entity.setDeletedAt(OffsetDateTime.now());
        this.save(entity);
    }

    public Page<TemplateWhats> getByFilters(String nome, Pageable pageable) {
        return this.dao.findByFilters(nome, pageable);
    }

    public TemplateWhats getByNome(String nome) {
        return this.dao.findFirstByNome(nome);
    }

}
