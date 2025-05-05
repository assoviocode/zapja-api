package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;

import java.util.List;

@Repository
public interface CampoCustomizadoDAO extends CrudRepository<CampoCustomizado, Long> {

        @Query(value = "SELECT cc FROM CampoCustomizado cc JOIN cc.tipoCampoCustomizado tcc WHERE " +
                        "(:rotulo IS NULL OR cc.rotulo = :rotulo) AND " +
                        "(:ativo IS NULL OR cc.ativo = :ativo) AND " +
                        "(:obrigatorio IS NULL OR cc.obrigatorio = :obrigatorio) AND " +
                        "(:tipoCampoCustomizadoId IS NULL OR tcc.id = :tipoCampoCustomizadoId)")
        List<CampoCustomizado> findByFilters(
                        @Param("rotulo") String rotulo,
                        @Param("ativo") Boolean ativo,
                        @Param("obrigatorio") Boolean obrigatorio,
                        @Param("tipoCampoCustomizadoId") Long tipoCampoCustomizadoId);


        @Query(value = "SELECT cc FROM CampoCustomizado cc WHERE cc.obrigatorio = true")
        List<CampoCustomizado> findAllObrigatorios();

}
