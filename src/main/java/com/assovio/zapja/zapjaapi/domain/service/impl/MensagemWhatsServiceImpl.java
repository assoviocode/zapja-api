package com.assovio.zapja.zapjaapi.domain.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.MensagemWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.model.MensagemWhats;
import com.assovio.zapja.zapjaapi.domain.service.MensagemWhatsService;

@Service
public class MensagemWhatsServiceImpl
                extends GenericServiceImpl<MensagemWhats, Long, MensagemWhatsDAO>
                implements MensagemWhatsService {

        @Override
        public Page<MensagemWhats> getByFilters(String texto, String templateWhatsUuid, Long clienteId,
                        Pageable pageable) {
                return this.dao.findByFilters(texto, templateWhatsUuid, clienteId, pageable);
        }

        @Override
        public MensagemWhats getByUuidAndTemplateWhatsAndCliente(String uuid, String templateWhatsUuid,
                        Long clienteId) {
                return this.dao.findFirstByUuidAndTemplateWhatsUuidAndClienteId(uuid, templateWhatsUuid, clienteId);
        }

        @Override
        public MensagemWhats getByTextoAndTemplateWhatsAndCliente(String texto, String templateWhatsUuid,
                        Long clienteId) {
                return this.dao.findFirstByTextoAndTemplateWhatsUuidAndClienteId(texto, templateWhatsUuid, clienteId);
        }

        @Override
        public MensagemWhats getByOrdemEnvioAndTemplateWhatsAndCliente(Integer ordemEnvio, String templateWhatsUuid,
                        Long clienteId) {
                return this.dao.findFirstByOrdemEnvioAndTemplateWhatsUuidAndClienteId(ordemEnvio, templateWhatsUuid,
                                clienteId);
        }

}
