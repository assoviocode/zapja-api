package com.assovio.zapja.zapjaapi.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.EnvioWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;

@Service
public class EnvioWhatsService {

    @Autowired
    private EnvioWhatsDAO dao;

    public List<EnvioWhats> getAll() {
        return (List<EnvioWhats>) this.dao.findAll();
    }

    public EnvioWhats getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public EnvioWhats save(EnvioWhats entity) {
        return this.dao.save(entity);
    }

    public void delete(EnvioWhats entity) {
        this.dao.delete(entity);
    }

    public void deleteLogical(EnvioWhats entity) {
        entity.setDeletedAt(LocalDateTime.now());
        this.save(entity);
    }

    public Page<EnvioWhats> getByFilters(String celularOrigem, Long templateWhatsId, Long contatoId,
            Pageable pageable) {
        return this.dao.findByFilters(celularOrigem, templateWhatsId, contatoId, pageable);
    }

}
