package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.MensagemWhats;

@Repository
public interface MensagemWhatsDAO extends CrudRepository<MensagemWhats, Long> {

        @Query(value = "SELECT mw FROM MensagemWhats mw "
                        + "WHERE (:texto IS NULL OR mw.texto LIKE CONCAT(:texto, '%')) "
                        + "AND :templateWhatsUuid = mw.templateWhats.uuid "
                        + "AND :clienteId = mw.cliente.id")
        Page<MensagemWhats> findByFilters(
                        @Param("texto") String texto,
                        @Param("templateWhatsUuid") String templateWhatsUuid,
                        @Param("clienteId") Long clienteId,
                        Pageable pageable);

        MensagemWhats findFirstByUuidAndTemplateWhatsUuidAndClienteId(String uuid, String templateWhatsUuid,
                        Long clienteId);

        MensagemWhats findFirstByTextoAndTemplateWhatsUuidAndClienteId(String texto, String templateWhatsUuid,
                        Long clienteId);

}
