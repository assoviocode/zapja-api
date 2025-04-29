package com.assovio.zapja.zapjaapi.domain.service;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.SetupEnvioWhats;

@Service
public interface SetupEnvioWhatsService extends GenericService<SetupEnvioWhats, Long> {

    SetupEnvioWhats getByUuidAndCliente(String uuid, Long clienteId);

}
