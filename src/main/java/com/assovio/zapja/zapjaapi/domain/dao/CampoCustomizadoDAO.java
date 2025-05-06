package com.assovio.zapja.zapjaapi.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;

@Repository
public interface CampoCustomizadoDAO extends CrudRepository<CampoCustomizado, Long> {

        @Query(value = "SELECT cc FROM CampoCustomizado cc JOIN cc.tipoCampoCustomizado tcc " +
                        "WHERE (:rotulo IS NULL OR cc.rotulo = :rotulo) " +
                        "AND (:ativo IS NULL OR cc.ativo = :ativo) " +
                        "AND (:obrigatorio IS NULL OR cc.obrigatorio = :obrigatorio) " +
                        "AND (:tipoCampoCustomizadoUuid IS NULL OR tcc.uuid = :tipoCampoCustomizadoUuid) " +
                        "AND cc.cliente.id = :clienteId")
        List<CampoCustomizado> findByFilters(
                        @Param("rotulo") String rotulo,
                        @Param("ativo") Boolean ativo,
                        @Param("obrigatorio") Boolean obrigatorio,
                        @Param("tipoCampoCustomizadoUuid") String tipoCampoCustomizadoUuid,
                        @Param("clienteId") Long clienteId);

        CampoCustomizado findFirstByUuidAndClienteId(String uuid, Long clienteId);

        @Query(value = "SELECT cc FROM CampoCustomizado cc WHERE cc.obrigatorio = true")
        List<CampoCustomizado> findAllObrigatorios();

}
