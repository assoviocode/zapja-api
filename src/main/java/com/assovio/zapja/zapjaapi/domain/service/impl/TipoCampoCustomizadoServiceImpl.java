package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.TipoCampoCustomizadoDAO;
import com.assovio.zapja.zapjaapi.domain.model.TipoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.service.TipoCampoCustomizadoService;

@Service
public class TipoCampoCustomizadoServiceImpl
                extends GenericServiceImpl<TipoCampoCustomizado, Long, TipoCampoCustomizadoDAO>
                implements TipoCampoCustomizadoService {

        @Override
        public TipoCampoCustomizado getByUuidAndCliente(String uuid, Long clienteId) {
                return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
        }

        @Override
        public TipoCampoCustomizado getByNome(String nome, Long clienteId) {
                return this.dao.findFirstByNomeAndClienteId(nome, clienteId);
        }

        @Override
        public List<TipoCampoCustomizado> getByFilters(String nome, Long clienteId) {
                return this.dao.findByFilters(nome, clienteId);
        }
}
