package com.assovio.zapja.zapjaapi.domain.daos;

import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumRoleUsuario;
import com.assovio.zapja.zapjaapi.domain.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

        UserDetails findByLogin(String login);

        Usuario findUsuarioByLogin(String login);

        Usuario findUsuarioByNome(String username);

        UserDetails findByEmail(String email);

        Usuario findTopByEmail(String email);

        List<Usuario> findAllByRole(EnumRoleUsuario usuariorole);

}
