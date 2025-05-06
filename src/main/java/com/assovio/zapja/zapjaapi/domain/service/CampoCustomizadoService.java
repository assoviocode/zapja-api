package com.assovio.zapja.zapjaapi.domain.service;

import java.util.List;

import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;

public interface CampoCustomizadoService extends GenericService<CampoCustomizado, Long> {

    public CampoCustomizado getByUuidAndCliente(String uuid, Long clienteId);

    public List<CampoCustomizado> getObrigatorios();

    public List<CampoCustomizado> getByFilters(String rotulo, Boolean ativo, Boolean obrigatorio,
            String tipoCampoCustomizadoUuid, Long clienteId);

}
