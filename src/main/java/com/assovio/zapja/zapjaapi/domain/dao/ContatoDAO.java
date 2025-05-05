package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.Contato;

@Repository
public interface ContatoDAO extends CrudRepository<Contato, Long> {

        @Query(value = "SELECT c FROM Contato c " +
                        "WHERE (:numeroWhats IS NULL OR c.numeroWhats = :numeroWhats) " +
                        "AND (:nome IS NULL OR c.nome LIKE CONCAT(:nome, '%')) " +
                        "AND :clienteId = c.cliente.id")
        Page<Contato> findByFilters(
                        @Param("numeroWhats") String numeroWhats,
                        @Param("nome") String nome,
                        @Param("clienteId") Long clienteId,
                        Pageable pageable);

        Contato findFirstByUuidAndClienteId(String uuid, Long clienteId);

        Contato findFirstByNumeroWhatsAndClienteId(String numeroWhats, Long clienteId);

}
