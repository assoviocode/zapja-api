package com.assovio.zapja.zapjaapi.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;

@Service
public interface EnvioWhatsService extends GenericService<EnvioWhats, Long> {

    public Page<EnvioWhats> getByFilters(String nomeContato, String numeroWhatsapp, EnumStatusEnvioWhats status,
            String celularOrigem,
            String templateWhatsUuid,
            String contatoUuid,
            Long clienteId,
            Pageable pageable);

    public EnvioWhats getByUuidAndCliente(String uuid, Long clienteId);

    public EnvioWhats getProximo(String celularOrigem, Long clienteId);
}
