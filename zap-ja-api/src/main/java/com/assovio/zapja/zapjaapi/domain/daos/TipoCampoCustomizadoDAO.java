package com.assovio.zapja.zapjaapi.domain.daos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.TipoCampoCustomizado;

@Repository
public interface TipoCampoCustomizadoDAO extends CrudRepository<TipoCampoCustomizado, Long> {

        @Query(value = "SELECT tcc FROM TipoCampoCustomizado tcc WHERE " +
                        "(:nome IS NULL OR tcc.nome like '%'||:nome||'%')")
        List<TipoCampoCustomizado> findByFilters(
                        @Param("nome") String nome);

        TipoCampoCustomizado findFirstByNome(String nome);
}
