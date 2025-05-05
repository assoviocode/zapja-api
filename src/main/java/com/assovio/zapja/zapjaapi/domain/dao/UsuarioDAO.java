package com.assovio.zapja.zapjaapi.domain.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assovio.zapja.zapjaapi.domain.model.Usuario;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

        Usuario findFirstByUuid(String uuid);

        Usuario findFirstByLogin(String login);

        Usuario findFirstByEmail(String email);

        Usuario findFirstByUuidAndClienteId(String uuid, Long clienteId);

}
