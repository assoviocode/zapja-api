package com.assovio.zapja.zapjaapi.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.TipoCampoCustomizado;

@Repository
public interface TipoCampoCustomizadoDAO extends CrudRepository<TipoCampoCustomizado, Long> {

        @Query(value = "SELECT tcc FROM TipoCampoCustomizado tcc " +
                        "WHERE (:nome IS NULL OR tcc.nome LIKE CONCAT(:nome, '%')) " +
                        "AND :clienteId = tcc.cliente.id")
        List<TipoCampoCustomizado> findByFilters(
                        @Param("nome") String nome,
                        @Param("clienteId") Long clienteId);

        TipoCampoCustomizado findFirstByUuidAndClienteId(String uuid, Long clienteId);

        TipoCampoCustomizado findFirstByNomeAndClienteId(String nome, Long clienteId);
}
