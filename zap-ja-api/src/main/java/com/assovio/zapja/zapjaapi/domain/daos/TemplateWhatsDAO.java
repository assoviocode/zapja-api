package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.TemplateWhats;

@Repository
public interface TemplateWhatsDAO extends CrudRepository<TemplateWhats, Long> {

        @Query(value = "SELECT tw FROM TemplateWhats tw WHERE " +
                        "(:nome IS NULL OR tw.nome like '%'||:nome||'%')")
        Page<TemplateWhats> findByFilters(
                        @Param("nome") String nome,
                        Pageable pageable);

        TemplateWhats findFirstByNome(String nome);

}
