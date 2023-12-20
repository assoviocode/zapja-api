package com.assovio.zapja.zapjaapi.domain.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.ContatoDAO;
import com.assovio.zapja.zapjaapi.domain.models.Contato;

@Service
public class ContatoService {

    @Autowired
    private ContatoDAO dao;

    public List<Contato> getAll() {
        return (List<Contato>) this.dao.findAll();
    }

    public Contato getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public Contato save(Contato entity) {
        return this.dao.save(entity);
    }

    public void delete(Contato entity) {
        this.dao.delete(entity);
    }

    public void deleteLogical(Contato entity) {
        entity.setDeletedAt(LocalDateTime.now());
        this.save(entity);
    }

    public Page<Contato> getByFilters(String numeroWhats, String nome, Pageable pageable) {
        return this.dao.findByFilters(numeroWhats, nome, pageable);
    }

}
