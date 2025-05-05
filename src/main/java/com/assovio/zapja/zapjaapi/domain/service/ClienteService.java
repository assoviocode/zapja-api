package com.assovio.zapja.zapjaapi.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.Cliente;

@Service
public interface ClienteService extends GenericService<Cliente, Long> {

    public Page<Cliente> getByFilters(String nome, Pageable pageable);

    public Cliente getByUuid(String uuid);

}
