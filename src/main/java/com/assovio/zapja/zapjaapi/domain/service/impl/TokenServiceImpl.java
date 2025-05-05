package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String generateTokenExpiration(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("4SS0V10PR4J4HMAC256");
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(this.genExpirationDate(8))
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("4SS0V10PR4J4HMAC256");
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Date genExpirationDate(int hours) {
        LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(hours);
        return Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
