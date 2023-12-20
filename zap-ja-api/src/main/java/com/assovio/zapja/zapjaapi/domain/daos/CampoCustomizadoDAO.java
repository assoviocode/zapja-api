package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.assovio.zapja.zapjaapi.domain.models.CampoCustomizado;

public interface CampoCustomizadoDAO extends CrudRepository<CampoCustomizado, Long> {

        @Query(value = "SELECT cc FROM CampoCustomizado cc WHERE " +
                        "(:rotulo IS NULL OR cc.rotulo = :rotulo) AND " +
                        "(:tipoCampoCustomizadoId IS NULL OR cc.tipoCampoCustomizado.id = :tipoCampoCustomizadoId)")
        Page<CampoCustomizado> findByFilters(
                        @Param("rotulo") String rotulo,
                        @Param("tipoCampoCustomizadoId") Long tipoCampoCustomizadoId,
                        Pageable pageable);

}
