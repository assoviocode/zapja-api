package com.assovio.zapja.zapjaapi.domain.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.assovio.zapja.zapjaapi.domain.model.Usuario;

public interface UsuarioService extends GenericService<Usuario, Long>, UserDetailsService {

    public Usuario getByLogin(String login);
    
    public Usuario getByUuid(String uuid);
    
    public Usuario getByEmail(String email);

    public Usuario getByUuidAndClienteId(String uuid, Long clienteId);

}
