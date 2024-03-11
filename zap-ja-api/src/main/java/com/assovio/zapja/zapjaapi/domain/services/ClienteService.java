package com.assovio.zapja.zapjaapi.domain.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.ClienteDAO;
import com.assovio.zapja.zapjaapi.domain.models.Cliente;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusBotCliente;

@Service
public class ClienteService {

    @Autowired
    private ClienteDAO dao;

    public List<Cliente> getAll() {
        return (List<Cliente>) this.dao.findAll();
    }

    public Cliente getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public Cliente save(Cliente entity) {
        return this.dao.save(entity);
    }

    public void delete(Cliente entity) {
        this.dao.delete(entity);
    }

    public void deleteLogical(Cliente entity) {
        entity.setDeletedAt(OffsetDateTime.now());
        this.save(entity);
    }

    public Page<Cliente> getByFilters(String nome, Pageable pageable) {
        return this.dao.findByFilters(nome, pageable);
    }

    public Cliente getByIdAndStatus(Long id, EnumStatusBotCliente statusBot) {
        return this.dao.findByIdAndStatusBot(id, statusBot);
    }

}
