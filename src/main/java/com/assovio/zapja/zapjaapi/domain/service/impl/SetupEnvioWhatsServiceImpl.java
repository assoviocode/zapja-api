package com.assovio.zapja.zapjaapi.domain.service.impl;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.SetupEnvioWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.model.SetupEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.service.SetupEnvioWhatsService;

@Service
public class SetupEnvioWhatsServiceImpl extends GenericServiceImpl<SetupEnvioWhats, Long, SetupEnvioWhatsDAO>
        implements SetupEnvioWhatsService {

    @Override
    public SetupEnvioWhats getByUuidAndCliente(String uuid, Long clienteId) {
        return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
    }

}
