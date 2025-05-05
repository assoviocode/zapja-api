package com.assovio.zapja.zapjaapi.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.TipoCampoCustomizado;

@Service
public interface TipoCampoCustomizadoService extends GenericService<TipoCampoCustomizado, Long> {

    public TipoCampoCustomizado getByNome(String nome, Long clienteId);

    public List<TipoCampoCustomizado> getByFilters(String nome, Long clienteId);

}
