package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.assovio.zapja.zapjaapi.domain.models.TemplateWhats;

public interface TemplateWhatsDAO extends CrudRepository<TemplateWhats, Long> {

        @Query(value = "SELECT tw FROM TemplateWhats tw WHERE " +
                        "(:nome IS NULL OR tw.nome = :nome)")
        Page<TemplateWhats> findByFilters(
                        @Param("nome") String nome,
                        Pageable pageable);

}
