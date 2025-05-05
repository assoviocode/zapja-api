package com.assovio.zapja.zapjaapi.domain.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.TemplateWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;
import com.assovio.zapja.zapjaapi.domain.service.TemplateWhatsService;

@Service
public class TemplateWhatsServiceImpl
                extends GenericServiceImpl<TemplateWhats, Long, TemplateWhatsDAO>
                implements TemplateWhatsService {

        @Override
        public Page<TemplateWhats> getByFilters(String nome, Boolean ativo, Long clienteId, Pageable pageable) {
                return this.dao.findByFilters(nome, ativo, clienteId, pageable);
        }

        @Override
        public TemplateWhats getByUuidAndCliente(String uuid, Long clienteId) {
                return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
        }

        @Override
        public TemplateWhats getByNomeAndCliente(String nome, Long clienteId) {
                return this.dao.findFirstByNomeAndClienteId(nome, clienteId);
        }

}
