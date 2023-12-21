package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;

@Repository
public interface ContatoCampoCustomizadoDAO extends CrudRepository<ContatoCampoCustomizado, Long> {

        @Query(value = "SELECT ccc FROM ContatoCampoCustomizado ccc WHERE " +
                        "(:valor IS NULL OR ccc.valor = :valor) AND " +
                        "(:contatoId IS NULL OR ccc.contato.id = :contatoId) AND " +
                        "(:campoCustomizadoId IS NULL OR ccc.campoCustomizado.id = :campoCustomizadoId)")
        Page<ContatoCampoCustomizado> findByFilters(
                        @Param("valor") String valor,
                        @Param("contatoId") Long contatoId,
                        @Param("campoCustomizadoId") Long campoCustomizadoId,
                        Pageable pageable);

        ContatoCampoCustomizado findFirstByContatoIdAndCampoCustomizadoId(Long contatoId, Long campoCustomizadoId);

}
