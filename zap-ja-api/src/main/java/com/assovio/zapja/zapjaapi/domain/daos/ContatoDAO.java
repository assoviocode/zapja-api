package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.Contato;

@Repository
public interface ContatoDAO extends CrudRepository<Contato, Long> {

        @Query(value = "SELECT c FROM Contato c WHERE " +
                        "(:numeroWhats IS NULL OR c.numeroWhats = :numeroWhats) AND " +
                        "(:nome IS NULL OR c.nome like '%'||:nome||'%')")
        Page<Contato> findByFilters(
                        @Param("numeroWhats") String numeroWhats,
                        @Param("nome") String nome,
                        Pageable pageable);

}
