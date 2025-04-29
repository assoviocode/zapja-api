package com.assovio.zapja.zapjaapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.ContatoCampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.model.Contato;
import com.assovio.zapja.zapjaapi.domain.model.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.ContatoCampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.service.ContatoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("contatos/{contatoUuid}/contatosCampoCustomizado")
public class ContatoCampoCustomizadoController {

    private ContatoService contatoService;
    private ContatoCampoCustomizadoService contatoCampoCustomizadoService;
    private ContatoCampoCustomizadoAssembler contatoCampoCustomizadoAssembler;

    @GetMapping
    public ResponseEntity<List<ContatoCampoCustomizadoResponseDTO>> index(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(name = "contato_id", required = false) String contatoUuid,
            @RequestParam(name = "campo_customizado_id", required = false) String campoCustomizadoUuid) {

        List<ContatoCampoCustomizado> result = this.contatoCampoCustomizadoService.getByFilters(contatoUuid,
                campoCustomizadoUuid, usuarioLogado.getClienteIdOrNull());

        List<ContatoCampoCustomizadoResponseDTO> responseDTOs = this.contatoCampoCustomizadoAssembler
                .toCollectionDTO(result);

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContatoCampoCustomizadoResponseDTO> show(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String contatoUuid,
            @PathVariable String uuid) {

        Contato contato = this.contatoService.getByUuidAndCliente(contatoUuid, usuarioLogado.getClienteIdOrNull());

        if (contato == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado!");
        }

        ContatoCampoCustomizado result = this.contatoCampoCustomizadoService
                .getByContatoAndCampoCustomizado(contato.getUuid(), uuid, usuarioLogado.getClienteIdOrNull());

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Contato Campo Customizado não encontrado!");
        }

        ContatoCampoCustomizadoResponseDTO responseDTO = this.contatoCampoCustomizadoAssembler.toDTO(result);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ContatoCampoCustomizadoResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String contatoUuid,
            @PathVariable String uuid,
            @Valid @RequestBody ContatoCampoCustomizadoRequestDTO requestDTO) {

        Contato contato = this.contatoService.getByUuidAndCliente(contatoUuid, usuarioLogado.getClienteIdOrNull());

        if (contato == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        ContatoCampoCustomizado entity = this.contatoCampoCustomizadoService
                .getByContatoAndCampoCustomizado(contato.getUuid(), uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Contato Campo Customizado não encontrado!");
        }

        entity = this.contatoCampoCustomizadoAssembler.toEntityUpdate(requestDTO, entity);

        entity = this.contatoCampoCustomizadoService.save(entity);

        ContatoCampoCustomizadoResponseDTO responseDTO = this.contatoCampoCustomizadoAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String contatoUuid,
            @PathVariable String uuid) {

        Contato contato = this.contatoService.getByUuidAndCliente(contatoUuid, usuarioLogado.getClienteIdOrNull());

        if (contato == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        ContatoCampoCustomizado entity = this.contatoCampoCustomizadoService
                .getByContatoAndCampoCustomizado(contato.getUuid(), uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Contato Campo Customizado não encontrado!");
        }

        this.contatoCampoCustomizadoService.deleteLogical(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
