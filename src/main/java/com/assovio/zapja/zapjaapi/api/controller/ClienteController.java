package com.assovio.zapja.zapjaapi.api.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assovio.zapja.zapjaapi.api.assembler.ClienteAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.ClienteRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ClienteResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.model.Cliente;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.ClienteService;
import com.assovio.zapja.zapjaapi.domain.service.NotificacaoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("clientes")
public class ClienteController {

    private ClienteService clienteService;
    private ClienteAssembler clienteAssembler;
    private NotificacaoService notificacaoService;

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> index(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size,
            @RequestParam(required = false) String nome) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<Cliente> result = this.clienteService.getByFilters(nome, paginacao);

        Page<ClienteResponseDTO> responseDTOs = this.clienteAssembler.toPageDTO(result);

        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> show(@PathVariable Long id) {

        Cliente result = this.clienteService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado!");
        }

        ClienteResponseDTO responseDTO = this.clienteAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> store(@Valid @RequestBody ClienteRequestDTO requestDTO) {

        Cliente result = this.clienteAssembler.toEntity(requestDTO);

        result = this.clienteService.save(result);

        ClienteResponseDTO responseDTO = this.clienteAssembler.toDTO(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO requestDTO) {

        Cliente entity = this.clienteService.getById(id);

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        entity = this.clienteAssembler.toEntity(requestDTO, entity);

        entity = this.clienteService.save(entity);

        ClienteResponseDTO responseDTO = this.clienteAssembler.toDTO(entity);
        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {

        Cliente result = this.clienteService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        this.clienteService.deleteLogical(result);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/qrCodeWhats/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void streamQrCodeWhats(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam("qr_code_whats") MultipartFile midiaFile) throws IOException {

        if (usuarioLogado.getCliente() == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        byte[] qrCodeBytes = midiaFile.getBytes();

        this.notificacaoService.sendQrCodeToAll(qrCodeBytes);
    }

}
