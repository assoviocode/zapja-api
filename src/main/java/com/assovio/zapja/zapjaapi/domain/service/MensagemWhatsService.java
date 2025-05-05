package com.assovio.zapja.zapjaapi.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.MensagemWhats;

@Service
public interface MensagemWhatsService extends GenericService<MensagemWhats, Long> {

    public Page<MensagemWhats> getByFilters(String texto, String templateWhatsUuid, Long clienteId, Pageable pageable);

    public MensagemWhats getByUuidAndTemplateWhatsAndCliente(String uuid, String templateWhatsUuid, Long clienteId);

    public MensagemWhats getByTextoAndTemplateWhatsAndCliente(String texto, String templateWhatsUuid, Long clienteId);

}
