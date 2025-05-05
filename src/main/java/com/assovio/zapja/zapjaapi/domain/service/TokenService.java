package com.assovio.zapja.zapjaapi.domain.service;

import com.assovio.zapja.zapjaapi.domain.model.Usuario;

public interface TokenService {
    public String generateTokenExpiration(Usuario usuario);

    public String validateToken(String token);
}
