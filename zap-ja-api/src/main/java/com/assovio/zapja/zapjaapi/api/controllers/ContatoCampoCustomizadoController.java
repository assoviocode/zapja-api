package com.assovio.zapja.zapjaapi.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assemblers.ContatoCampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.models.Contato;
import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.services.ContatoCampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.services.ContatoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("contatos/{contatoId}/contatosCampoCustomizado")
public class ContatoCampoCustomizadoController {

    private ContatoService contatoService;
    private ContatoCampoCustomizadoService contatoCampoCustomizadoService;
    private ContatoCampoCustomizadoAssembler contatoCampoCustomizadoAssembler;

    @GetMapping("/{id}")
    public ResponseEntity<ContatoCampoCustomizadoResponseDTO> show(
            @PathVariable Long contatosId,
            @PathVariable Long id) {

        Contato contato = this.contatoService.getById(contatosId);

        if (contato == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado!");
        }

        ContatoCampoCustomizado result = this.contatoCampoCustomizadoService
                .getByContatoAndCampoCustomizado(contato.getId(), id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Contato Campo Customizado não encontrado!");
        }

        ContatoCampoCustomizadoResponseDTO responseDTO = this.contatoCampoCustomizadoAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoCampoCustomizadoResponseDTO> update(
            @PathVariable Long contatosId,
            @PathVariable Long id,
            @Valid @RequestBody ContatoCampoCustomizadoRequestDTO requestDTO) {

        Contato contato = this.contatoService.getById(contatosId);

        if (contato == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        ContatoCampoCustomizado resultNaoEditado = this.contatoCampoCustomizadoService
                .getByContatoAndCampoCustomizado(contato.getId(), id);

        if (resultNaoEditado == null) {
            throw new EntidadeNaoEncontradaException("Contato Campo Customizado não encontrado!");
        }

        ContatoCampoCustomizado resultEditado = this.contatoCampoCustomizadoAssembler.toEntity(requestDTO);
        resultEditado.setId(id);

        resultEditado = this.contatoCampoCustomizadoService.save(resultEditado);

        ContatoCampoCustomizadoResponseDTO responseDTO = this.contatoCampoCustomizadoAssembler.toDTO(resultEditado);

        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(
            @PathVariable Long contatosId,
            @PathVariable Long id) {

        Contato contato = this.contatoService.getById(contatosId);

        if (contato == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        ContatoCampoCustomizado result = this.contatoCampoCustomizadoService
                .getByContatoAndCampoCustomizado(contato.getId(), id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Contato Campo Customizado não encontrado!");
        }

        this.contatoCampoCustomizadoService.deleteLogical(result);

        return ResponseEntity.noContent().build();
    }

}
