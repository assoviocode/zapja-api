package com.assovio.zapja.zapjaapi.domain.daos;

import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EnvioWhatsDAO extends CrudRepository<EnvioWhats, Long> {

        @Query(value = "SELECT ew FROM EnvioWhats ew JOIN ew.contato c WHERE "
                        + "(:nomeContato IS NULL OR c.nome = :nomeContato) AND "
                        + "(:numeroWhatsapp IS NULL OR c.numeroWhats = :numeroWhatsapp) AND "
                        + "(:status IS NULL OR ew.status = :status) AND "
                        + "(:celularOrigem IS NULL OR ew.celularOrigem = :celularOrigem) AND "
                        + "(:templateWhatsId IS NULL OR ew.templateWhats.id = :templateWhatsId) AND "
                        + "(:dataPrevista IS NULL OR ew.dataPrevista <= CAST(STR_TO_DATE(:dataPrevista, '%Y-%m-%d') AS DATE)) AND "
                        + "(:contatoId IS NULL OR ew.contato.id = :contatoId)")
        Page<EnvioWhats> findByFilters(
                        @Param("nomeContato") String nomeContato,
                        @Param("numeroWhatsapp") String numeroWhatsapp,
                        @Param("status") EnumStatusEnvioWhats status,
                        @Param("celularOrigem") String celularOrigem,
                        @Param("templateWhatsId") Long templateWhatsId,
                        @Param("contatoId") Long contatoId,
                        @Param("dataPrevista") Date dataPrevista,
                        Pageable pageable);


        @Query(value = "SELECT ew FROM EnvioWhats ew WHERE "
                        + "(:status IS NULL OR ew.status = :status) AND "
                        + "ew.dataPrevista <= CAST(STR_TO_DATE(NOW(), '%Y-%m-%d') AS DATE)")
        List<EnvioWhats> findFirstByStatus(
                        @Param("status") EnumStatusEnvioWhats status);


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
                "and ew.data_prevista <= CAST(STR_TO_DATE(NOW(), '%Y-%m-%d') AS DATE) " +
                "order by ew.id " +
                "limit 1" , nativeQuery = true)
        List<EnvioWhats> findFirstValidByStatus(@Param("status") String status);



}
