package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.UsuarioDAO;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.UsuarioService;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Long, UsuarioDAO>
        implements UsuarioService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.dao.findFirstByLogin(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    @Override
    public Usuario getByUuid(String uuid) {
        return this.dao.findFirstByUuid(uuid);
    }

    @Override
    public Usuario getByLogin(String login) {
        return this.dao.findFirstByLogin(login);
    }

    @Override
    public Usuario getByEmail(String email) {
        return this.dao.findFirstByEmail(email);
    }

    @Override
    public Usuario getByUuidAndClienteId(String uuid, Long clienteId) {
        return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
    }
}
