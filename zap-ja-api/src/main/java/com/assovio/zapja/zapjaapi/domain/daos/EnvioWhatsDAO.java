package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;

public interface EnvioWhatsDAO extends CrudRepository<EnvioWhats, Long> {

        @Query(value = "SELECT ew FROM EnvioWhats ew WHERE " +
                        "(:celularOrigem IS NULL OR ew.celularOrigem = :celularOrigem) AND " +
                        "(:templateWhatsId IS NULL OR ew.templateWhats.id = :templateWhatsId) AND " +
                        "(:contatoId IS NULL OR ew.contato.id = :contatoId)")
        Page<EnvioWhats> findByFilters(
                        @Param("celularOrigem") String celularOrigem,
                        @Param("templateWhatsId") Long templateWhatsId,
                        @Param("contatoId") Long contatoId,
                        Pageable pageable);

}
