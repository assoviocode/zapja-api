package com.assovio.zapja.zapjaapi.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.ContatoCampoCustomizado;

@Repository
public interface ContatoCampoCustomizadoDAO extends CrudRepository<ContatoCampoCustomizado, Long> {

        @Query(value = "SELECT ccc FROM ContatoCampoCustomizado ccc " +
                        "WHERE (:contatoUuid IS NULL OR ccc.contato.uuid = :contatoUuid) " +
                        "AND (:campoCustomizadoUuid IS NULL OR ccc.campoCustomizado.uuid = :campoCustomizadoUuid) " +
                        "AND :clienteId = ccc.cliente.id")
        List<ContatoCampoCustomizado> findByFilters(
                        @Param("contatoUuid") String contatoUuid,
                        @Param("campoCustomizadoUuid") String campoCustomizadoUuid,
                        @Param("clienteId") Long clienteId);

        ContatoCampoCustomizado findFirstByContatoUuidAndCampoCustomizadoUuidAndClienteId(String contatoUuid,
                        String campoCustomizadoUuid,
                        Long clienteId);

        ContatoCampoCustomizado findFirstByUuidAndClienteId(String uuid, Long clienteId);

}
