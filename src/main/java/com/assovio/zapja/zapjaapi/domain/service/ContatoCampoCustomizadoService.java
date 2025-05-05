package com.assovio.zapja.zapjaapi.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.ContatoCampoCustomizado;

@Service
public interface ContatoCampoCustomizadoService extends GenericService<ContatoCampoCustomizado, Long> {

    public List<ContatoCampoCustomizado> getByFilters(String contatoUuid, String campoCustomizadoUuid, Long clienteId);

    public ContatoCampoCustomizado getByContatoAndCampoCustomizado(String contatoUuid, String campoCustomizadoUuid,
            Long clienteId);

    public ContatoCampoCustomizado getByUuidAndCliente(String uuid, Long clienteId);

}
