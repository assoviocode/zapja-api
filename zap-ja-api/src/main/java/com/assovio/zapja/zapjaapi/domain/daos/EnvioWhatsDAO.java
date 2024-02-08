package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;

@Repository
public interface EnvioWhatsDAO extends CrudRepository<EnvioWhats, Long> {

        @Query(value = "SELECT ew FROM EnvioWhats ew JOIN ew.contato c WHERE "
                        + "(:nomeContato IS NULL OR c.nome = :nomeContato) AND "
                        + "(:numeroWhatsapp IS NULL OR c.numeroWhats = :numeroWhatsapp) AND "
                        + "(:status IS NULL OR ew.status = :status) AND "
                        + "(:celularOrigem IS NULL OR ew.celularOrigem = :celularOrigem) AND "
                        + "(:templateWhatsId IS NULL OR ew.templateWhats.id = :templateWhatsId) AND "
                        + "(:contatoId IS NULL OR ew.contato.id = :contatoId)")
        Page<EnvioWhats> findByFilters(
                        @Param("nomeContato") String nomeContato,
                        @Param("numeroWhatsapp") String numeroWhatsapp,
                        @Param("status") EnumStatusEnvioWhats status,
                        @Param("celularOrigem") String celularOrigem,
                        @Param("templateWhatsId") Long templateWhatsId,
                        @Param("contatoId") Long contatoId,
                        Pageable pageable);

        EnvioWhats findFirstByStatus(EnumStatusEnvioWhats status);

}
