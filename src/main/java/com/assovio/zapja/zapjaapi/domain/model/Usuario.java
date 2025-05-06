package com.assovio.zapja.zapjaapi.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumPerfilUsuario;
import com.assovio.zapja.zapjaapi.domain.model.contracts.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SQLRestriction("deleted_at IS NULL")
@Table(name = "usuario")
public class Usuario extends EntityBase implements UserDetails {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "perfil", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumPerfilUsuario perfil;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Usuario(String nome, String email, String login, String senha, EnumPerfilUsuario perfil, Cliente cliente) {
        this.login = login;
        this.nome = nome;
        this.email = email;
        this.senha = new BCryptPasswordEncoder().encode(senha.trim());
        this.perfil = perfil;
        this.cliente = cliente;

        if (this.login == null || this.login.isBlank())
            this.login = this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        switch (this.perfil) {
            case MASTER -> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_MASTER"),
                    new SimpleGrantedAuthority("ROLE_ROBO"),
                    new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"),
                    new SimpleGrantedAuthority("ROLE_OPERACIONAL"));
            case ROBO -> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_ROBO"));
            case ADMINISTRADOR -> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"),
                    new SimpleGrantedAuthority("ROLE_OPERACIONAL"));
            case OPERACIONAL -> authorities = List.of(new SimpleGrantedAuthority("ROLE_OPERACIONAL"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getClienteIdOrNull() {
        if (this.cliente != null) {
            return this.cliente.getId();
        }

        return null;

    }
}
