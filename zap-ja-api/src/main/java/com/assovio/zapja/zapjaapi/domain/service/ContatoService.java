package com.assovio.zapja.zapjaapi.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.Contato;

@Service
public interface ContatoService extends GenericService<Contato, Long> {

    public Page<Contato> getByFilters(String numeroWhats, String nome, Long clienteId, Pageable pageable);

    public Contato getByUuidAndCliente(String uuid, Long clienteId);

    public Contato getByNumeroWhatsAndCliente(String numeroWhats, Long clienteId);

}
