package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.TipoCampoCustomizado;

@Repository
public interface TipoCampoCustomizadoDAO extends CrudRepository<TipoCampoCustomizado, Long> {

    @Query(value = "SELECT tcc FROM TipoCampoCustomizado tcc WHERE " +
            "(:nome IS NULL OR tcc.nome like '%'||:nome||'%') AND " +
            "(:mascara IS NULL OR tcc.mascara = :mascara)")
    Page<TipoCampoCustomizado> findByFilters(
            @Param("nome") String nome,
            @Param("mascara") String mascara,
            Pageable pageable);
}
