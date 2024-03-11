package com.assovio.zapja.zapjaapi.api.controllers;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assovio.zapja.zapjaapi.api.assemblers.ClienteAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.ClienteRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ClienteResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.models.Cliente;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusBotCliente;
import com.assovio.zapja.zapjaapi.domain.services.ClienteService;
import com.assovio.zapja.zapjaapi.domain.services.NotificacaoService;

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
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "ativo", required = false) Boolean ativo) {

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

    @PostMapping("/{id}/qrCodeWhats/upload")
    public ResponseEntity<ClienteResponseDTO> storeQrCodeWhats(@PathVariable Long id,
            @RequestParam("qr_code_whats") MultipartFile midiaFile)
            throws IOException {

        Cliente entity = this.clienteService.getById(id);

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        entity.setQrCodeWhats(midiaFile.getBytes());

        entity = this.clienteService.save(entity);

        this.notificacaoService.sendNotificationToAll("QR Code salvo com sucesso!");

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{id}/qrCodeWhats/download")
    public ResponseEntity<byte[]> downloadQrCodeWhats(@PathVariable Long id)
            throws IOException {

        Cliente entity = this.clienteService.getById(id);

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        if (entity.getQrCodeWhats() != null) {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("image/png"));

            return new ResponseEntity<byte[]>(entity.getQrCodeWhats(), headers, HttpStatus.OK);

        }

        return null;
    }

    @PutMapping("/{id}/iniciarEnvio")
    public ResponseEntity<HttpStatus> iniciarEnvio(@PathVariable Long id) {
        Cliente entity = this.clienteService.getById(id);

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        entity.setStatusBot(EnumStatusBotCliente.ENVIANDO);

        entity = this.clienteService.save(entity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/pararEnvio")
    public ResponseEntity<HttpStatus> pararEnvio(@PathVariable Long id) {
        Cliente entity = this.clienteService.getById(id);

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        entity.setStatusBot(EnumStatusBotCliente.PARADO);

        entity = this.clienteService.save(entity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/autenticado")
    public ResponseEntity<HttpStatus> updateQrCodeValido(@PathVariable Long id) {
        Cliente entity = this.clienteService.getById(id);

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }

        entity.setQrCodeWhats(null);

        entity = this.clienteService.save(entity);

        this.notificacaoService.sendNotificationToAll("QR Code autenticado com sucesso!");

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
