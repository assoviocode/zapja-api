package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.RoboClienteDAO;
import com.assovio.zapja.zapjaapi.domain.model.RoboCliente;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusRoboCliente;
import com.assovio.zapja.zapjaapi.domain.service.RoboClienteService;

@Service
public class RoboClienteServiceImpl extends GenericServiceImpl<RoboCliente, Long, RoboClienteDAO>
                implements RoboClienteService {

        @Override
        public List<RoboCliente> getByFilters(String celularOrigem, EnumStatusRoboCliente status, Long clienteId) {
                return this.dao.findByFilters(celularOrigem, status, clienteId);
        }

        @Override
        public RoboCliente getByUuidAndCliente(String uuid, Long clienteId) {
                return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
        }
}
