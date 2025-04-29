package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.SetupEnvioWhats;

@Repository
public interface SetupEnvioWhatsDAO extends CrudRepository<SetupEnvioWhats, Long> {

    SetupEnvioWhats findFirstByUuidAndClienteId(String uuid, Long clienteId);

}
