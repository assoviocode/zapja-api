package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.ContatoCampoCustomizadoDAO;
import com.assovio.zapja.zapjaapi.domain.model.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.service.ContatoCampoCustomizadoService;

@Service
public class ContatoCampoCustomizadoServiceImpl
                extends GenericServiceImpl<ContatoCampoCustomizado, Long, ContatoCampoCustomizadoDAO>
                implements ContatoCampoCustomizadoService {

        @Override
        public List<ContatoCampoCustomizado> getByFilters(String contatoUuid, String campoCustomizadoUuid,
                        Long clienteId) {
                return this.dao.findByFilters(contatoUuid, campoCustomizadoUuid, clienteId);
        }

        @Override
        public ContatoCampoCustomizado getByContatoAndCampoCustomizado(String contatoUuid, String campoCustomizadoUuid,
                        Long clienteId) {
                return this.dao.findFirstByContatoUuidAndCampoCustomizadoUuidAndClienteId(contatoUuid,
                                campoCustomizadoUuid,
                                clienteId);
        }

        @Override
        public ContatoCampoCustomizado getByUuidAndCliente(String uuid, Long clienteId) {
                return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
        }

}
