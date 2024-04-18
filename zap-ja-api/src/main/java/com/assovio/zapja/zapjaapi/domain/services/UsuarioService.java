package com.assovio.zapja.zapjaapi.domain.services;

import com.assovio.zapja.zapjaapi.domain.daos.UsuarioDAO;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumRoleUsuario;
import com.assovio.zapja.zapjaapi.domain.models.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioDAO dao;

	public UserDetails GetUsuarioExistenteByLogin(String login) {
		return this.dao.findByLogin(login);
	}

	public Usuario GetUsuarioByLogin(String login) {
		return this.dao.findUsuarioByLogin(login);
	}

	public UserDetails GetUsuarioExistenteByEmail(String email) {
		return this.dao.findByEmail(email);
	}

	public List<Usuario> GetUsuarioByRole(EnumRoleUsuario role) {
		return this.dao.findAllByRole(role);
	}

	@Transactional
	public Usuario Save(Usuario usuario) {
		return this.dao.save(usuario);
	}

	public Usuario GetById(Long id) {
		return this.dao.findById(id).orElse(null);
	}

	public Usuario GetByEmail(String email) {
		return this.dao.findTopByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.dao.findUsuarioByNome(username);
	}
}