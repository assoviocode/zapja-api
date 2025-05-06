package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.CampoCustomizadoDAO;
import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.service.CampoCustomizadoService;

@Service
public class CampoCustomizadoServiceImpl
                extends GenericServiceImpl<CampoCustomizado, Long, CampoCustomizadoDAO>
                implements CampoCustomizadoService {

        @Override
        public CampoCustomizado getByUuidAndCliente(String uuid, Long clienteId) {
                return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
        }

        @Override
        public List<CampoCustomizado> getObrigatorios() {
                return this.dao.findAllObrigatorios();
        }

        @Override
        public List<CampoCustomizado> getByFilters(String rotulo, Boolean ativo, Boolean obrigatorio,
                        String tipoCampoCustomizadoUuid, Long clienteId) {
                return this.dao.findByFilters(rotulo, ativo, obrigatorio, tipoCampoCustomizadoUuid, clienteId);
        }
}
