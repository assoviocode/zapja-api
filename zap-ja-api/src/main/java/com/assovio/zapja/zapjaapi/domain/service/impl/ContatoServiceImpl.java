package com.assovio.zapja.zapjaapi.domain.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.ContatoDAO;
import com.assovio.zapja.zapjaapi.domain.model.Contato;
import com.assovio.zapja.zapjaapi.domain.service.ContatoService;

@Service
public class ContatoServiceImpl
                extends GenericServiceImpl<Contato, Long, ContatoDAO>
                implements ContatoService {

        @Override
        public Page<Contato> getByFilters(String numeroWhats, String nome, Long clienteId, Pageable pageable) {
                return this.dao.findByFilters(numeroWhats, nome, clienteId, pageable);
        }

        @Override
        public Contato getByUuidAndCliente(String uuid, Long clienteId) {
                return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
        }

        @Override
        public Contato getByNumeroWhatsAndCliente(String numeroWhats, Long clienteId) {
                return this.dao.findFirstByNumeroWhatsAndClienteId(numeroWhats, clienteId);
        }

}
