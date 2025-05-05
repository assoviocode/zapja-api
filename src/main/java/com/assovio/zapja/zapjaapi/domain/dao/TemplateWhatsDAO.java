package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;

@Repository
public interface TemplateWhatsDAO extends CrudRepository<TemplateWhats, Long> {

        @Query(value = "SELECT tw FROM TemplateWhats tw "
                        + "WHERE (:nome IS NULL OR tw.nome LIKE CONCAT(:nome, '%')) "
                        + "AND (:ativo IS NULL OR tw.ativo = :ativo) "
                        + "AND :clienteId = tw.cliente.id")
        Page<TemplateWhats> findByFilters(
                        @Param("nome") String nome,
                        @Param("ativo") Boolean ativo,
                        @Param("clienteId") Long clienteId,
                        Pageable pageable);

        TemplateWhats findFirstByUuidAndClienteId(String uuid, Long clienteId);

        TemplateWhats findFirstByNomeAndClienteId(String nome, Long clienteId);

}
