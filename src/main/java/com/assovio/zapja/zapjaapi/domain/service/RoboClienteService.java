package com.assovio.zapja.zapjaapi.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.RoboCliente;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusRoboCliente;

@Service
public interface RoboClienteService extends GenericService<RoboCliente, Long> {

    public List<RoboCliente> getByFilters(String celularOrigem, EnumStatusRoboCliente status, Long clienteId);

    public RoboCliente getByUuidAndCliente(String uuid, Long clienteId);

}
