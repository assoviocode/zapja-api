package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.Cliente;
import com.assovio.zapja.zapjaapi.domain.model.RoboCliente;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusRoboCliente;

@Repository
public interface RoboClienteDAO extends CrudRepository<RoboCliente, Long> {

        @Query(value = "SELECT rc FROM RoboCliente rc WHERE " +
                        "(:celularOrigem IS NULL OR rc.celularOrigem LIKE CONCAT(:celularOrigem, '%'))")
        Page<Cliente> findByFilters(
                        @Param("celularOrigem") String celularOrigem,
                        Pageable pageable);

        RoboCliente findFirstByClienteIdAndStatus(Long clienteId, EnumStatusRoboCliente status);

}
