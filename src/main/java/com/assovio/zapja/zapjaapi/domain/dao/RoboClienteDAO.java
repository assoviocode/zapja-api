package com.assovio.zapja.zapjaapi.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.RoboCliente;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusRoboCliente;

@Repository
public interface RoboClienteDAO extends CrudRepository<RoboCliente, Long> {

        @Query(value = "SELECT rc FROM RoboCliente rc " +
                        "WHERE (:celularOrigem IS NULL OR rc.celularOrigem LIKE CONCAT(:celularOrigem, '%')) " +
                        "AND (:status IS NULL OR rc.status = :status) " +
                        "AND rc.cliente.id = :clienteId")
        List<RoboCliente> findByFilters(
                        @Param("celularOrigem") String celularOrigem,
                        @Param("status") EnumStatusRoboCliente status,
                        @Param("clienteId") Long clienteId);

        RoboCliente findFirstByUuidAndClienteId(String uuid, Long clienteId);

}
