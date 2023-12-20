package com.assovio.zapja.zapjaapi.domain.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.models.TipoCampoCustomizado;

@Repository
public interface TipoCampoCustomizadoDAO extends CrudRepository<TipoCampoCustomizado, Long> {
}
