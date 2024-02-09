package com.assovio.zapja.zapjaapi.domain.daos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;

@Repository
public interface ContatoCampoCustomizadoDAO extends CrudRepository<ContatoCampoCustomizado, Long> {

        @Query(value = "SELECT ccc FROM ContatoCampoCustomizado ccc WHERE " +
                        "(:contatoId IS NULL OR ccc.contato.id = :contatoId) AND " +
                        "(:campoCustomizadoId IS NULL OR ccc.campoCustomizado.id = :campoCustomizadoId)")
        List<ContatoCampoCustomizado> findByFilters(
                        @Param("contatoId") Long contatoId,
                        @Param("campoCustomizadoId") Long campoCustomizadoId);

        ContatoCampoCustomizado findFirstByContatoIdAndCampoCustomizadoId(Long contatoId, Long campoCustomizadoId);

}
