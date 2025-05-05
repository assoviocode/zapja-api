package com.assovio.zapja.zapjaapi.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;

@Service
public interface TemplateWhatsService extends GenericService<TemplateWhats, Long> {

    public Page<TemplateWhats> getByFilters(String nome, Boolean ativo, Long clienteId, Pageable pageable);

    public TemplateWhats getByUuidAndCliente(String uuid, Long clienteId);

    public TemplateWhats getByNomeAndCliente(String nome, Long clienteId);

}
