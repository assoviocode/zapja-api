package com.assovio.zapja.zapjaapi.domain.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;

@Repository
public interface EnvioWhatsDAO extends CrudRepository<EnvioWhats, Long> {

        @Query(value = "SELECT ew FROM EnvioWhats ew JOIN ew.contato c "
                        + "WHERE (:nomeContato IS NULL OR c.nome = :nomeContato) "
                        + "AND (:numeroWhatsapp IS NULL OR c.numeroWhats = :numeroWhatsapp) "
                        + "AND (:status IS NULL OR ew.status = :status) "
                        + "AND (:celularOrigem IS NULL OR ew.celularOrigem = :celularOrigem) "
                        + "AND (:templateWhatsUuid IS NULL OR ew.templateWhats.uuid = :templateWhatsUuid) "
                        + "AND (:contatoUuid IS NULL OR ew.contato.uuid = :contatoUuid) "
                        + "AND (:clienteId IS NULL OR ew.contato.id = :clienteId)")
        Page<EnvioWhats> findByFilters(
                        @Param("nomeContato") String nomeContato,
                        @Param("numeroWhatsapp") String numeroWhatsapp,
                        @Param("status") EnumStatusEnvioWhats status,
                        @Param("celularOrigem") String celularOrigem,
                        @Param("templateWhatsUuid") String templateWhatsUuid,
                        @Param("contatoUuid") String contatoUuid,
                        @Param("clienteId") Long clienteId,
                        Pageable pageable);

        EnvioWhats findFirstByCelularOrigemAndStatusAndClienteId(
                        @Param("celularOrigem") String celularOrigem,
                        @Param("status") EnumStatusEnvioWhats status,
                        @Param("clienteId") Long clienteId);

        EnvioWhats findFirstByUuidAndClienteId(String uuid, Long clienteId);

        @Query(value = "select ew.* from envio_whats ew " +
                        "where ew.contato_id in " +
                        "( " +
                        " select c.id from contato c " +
                        " where c.deleted_at is null " +
                        " and ( " +
                        " select count(*) from contato_campo_customizado ccc " +
                        " inner join campo_customizado cc " +
                        " on ccc.campo_customizado_id = cc.id " +
                        " where ccc.contato_id = c.id " +
                        " and ccc.deleted_at is null " +
                        " and cc.obrigatorio = true " +
                        " )  " +
                        " =  " +
                        " ( " +
                        " select count(*) from campo_customizado cc  " +
                        " where cc.obrigatorio = true  " +
                        " and cc.deleted_at is null " +
                        " ) " +
                        ") " +
                        "and ew.deleted_at is null " +
                        "and (:status is null OR ew.status = :status) " +
                        "order by ew.id " +
                        "limit 1", nativeQuery = true)
        List<EnvioWhats> findFirstValidByStatus(@Param("status") String status);

}
