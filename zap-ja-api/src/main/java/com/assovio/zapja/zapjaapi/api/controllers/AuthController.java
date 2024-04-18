package com.assovio.zapja.zapjaapi.api.controllers;

import com.assovio.zapja.zapjaapi.api.assemblers.UsuarioAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.LoginRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.request.RegisterRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.LoginResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NaoAutorizadoException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.models.Cliente;
import com.assovio.zapja.zapjaapi.domain.models.Usuario;
import com.assovio.zapja.zapjaapi.domain.services.ClienteService;
import com.assovio.zapja.zapjaapi.domain.services.TokenService;
import com.assovio.zapja.zapjaapi.domain.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            Usuario usuario = (Usuario) auth.getPrincipal();

            var token = this.tokenService.generateToken(usuario);

            if (token != null && !token.isBlank()) {

                LoginResponseDTO loginResponseDTO = this.usuarioAssembler.toDTO(usuario);
                loginResponseDTO.setToken(token);

                return ResponseEntity.ok(loginResponseDTO);

            }
        } catch (RuntimeException e) {
            throw new NaoAutorizadoException("Usuario ou senha inválidos!");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO data) {


        if (this.usuarioService.GetUsuarioExistenteByLogin(data.login()) != null) {
            throw new NegocioException("Já existe uma conta com um login parecido!.");
        }

        if (this.usuarioService.GetUsuarioExistenteByEmail(data.email()) != null) {
            throw new NegocioException("Email já cadastrado no sistema!");
        }

        Cliente cliente = clienteService.getById(data.clienteId());
        if(cliente == null){
            throw new EntidadeNaoEncontradaException("O Cliente Informado não existe");
        }

        Usuario novoUsuario = new Usuario(data.nome(), data.login(), data.email(), data.senha(), data.role() , cliente);

        Usuario UsuarioCriado = this.usuarioService.Save(novoUsuario);

        if (UsuarioCriado == null) {
            throw new NegocioException("Erro ao criar conta.");
        }

        return ResponseEntity.ok().build();

    }

}
