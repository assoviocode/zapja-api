package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.Cliente;

@Repository
public interface ClienteDAO extends CrudRepository<Cliente, Long> {

        @Query(value = "SELECT c FROM Cliente c WHERE " +
                        "(:nome IS NULL OR c.nome LIKE '%'||:nome||'%')")
        Page<Cliente> findByFilters(
                        @Param("nome") String nome,
                        Pageable pageable);

        Cliente findFirstByUuid(String uuid);
}
