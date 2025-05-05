package com.assovio.zapja.zapjaapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.UsuarioAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.LoginRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.request.RegisterRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.LoginResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exception.NaoAutorizadoException;
import com.assovio.zapja.zapjaapi.domain.exception.NegocioException;
import com.assovio.zapja.zapjaapi.domain.model.Cliente;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.ClienteService;
import com.assovio.zapja.zapjaapi.domain.service.TokenService;
import com.assovio.zapja.zapjaapi.domain.service.UsuarioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    UsuarioAssembler usuarioAssembler;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),
                    data.senha());

            var auth = this.authenticationManager.authenticate(usernamePassword);

            Usuario usuario = (Usuario) auth.getPrincipal();

            if (usuario == null) {
                throw new NaoAutorizadoException("Usuário não encontrado!");
            }

            var token = this.tokenService.generateTokenExpiration(usuario);

            LoginResponseDTO responseDTO = this.usuarioAssembler.toDTO(usuario);
            responseDTO.setToken(token);

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (NaoAutorizadoException nae) {
            throw nae;
        } catch (RuntimeException e) {
            throw new NaoAutorizadoException("Usuário ou senha inválidos!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO data) {

        if (this.usuarioService.getByLogin(data.login()) != null) {
            throw new NegocioException("Já existe uma conta com um login parecido!.");
        }

        if (this.usuarioService.getByEmail(data.email()) != null) {
            throw new NegocioException("Email já cadastrado no sistema!");
        }

        Cliente cliente = clienteService.getById(data.clienteId());
        if (cliente == null) {
            throw new EntidadeNaoEncontradaException("O Cliente Informado não existe");
        }

        Usuario novoUsuario = new Usuario(data.nome(), data.email(), data.login(), data.senha(), data.perfil(),
                cliente);

        Usuario UsuarioCriado = this.usuarioService.save(novoUsuario);

        if (UsuarioCriado == null) {
            throw new NegocioException("Erro ao criar conta.");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
