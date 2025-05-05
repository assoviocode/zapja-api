package com.assovio.zapja.zapjaapi.api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.TemplateWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.TemplateWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.TemplateWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.TemplateWhatsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("templatesWhats")
public class TemplateWhatsController {

    private TemplateWhatsService templateWhatsService;
    private TemplateWhatsAssembler templateWhatsAssembler;

    @GetMapping
    public ResponseEntity<Page<TemplateWhatsResponseDTO>> index(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativo) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<TemplateWhats> result = this.templateWhatsService.getByFilters(nome, ativo,
                usuarioLogado.getClienteIdOrNull(), paginacao);

        Page<TemplateWhatsResponseDTO> responseDTOs = this.templateWhatsAssembler.toPageDTO(result);

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TemplateWhatsResponseDTO> show(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        TemplateWhats result = this.templateWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        TemplateWhatsResponseDTO responseDTO = this.templateWhatsAssembler.toDTO(result);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TemplateWhatsResponseDTO> store(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @Valid @RequestBody TemplateWhatsRequestDTO requestDTO) {

        TemplateWhats entityExistente = templateWhatsService.getByNomeAndCliente(
                requestDTO.getNome(),
                usuarioLogado.getClienteIdOrNull());

        if (entityExistente != null) {
            throw new NegocioException("Já existe um template cadastrado com esse nome");
        }

        TemplateWhats entity = templateWhatsAssembler.toEntity(requestDTO);
        entity.setCliente(usuarioLogado.getCliente());

        TemplateWhats templateSalvo = templateWhatsService.save(entity);

        TemplateWhatsResponseDTO responseDTO = templateWhatsAssembler.toDTO(templateSalvo);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TemplateWhatsResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid,
            @Valid @RequestBody TemplateWhatsRequestDTO requestDTO) {

        TemplateWhats entity = this.templateWhatsService.getByUuidAndCliente(uuid,
                usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado");
        }

        if (!entity.getNome().equalsIgnoreCase(requestDTO.getNome())) {
            TemplateWhats result = this.templateWhatsService.getByNomeAndCliente(requestDTO.getNome(),
                    usuarioLogado.getClienteIdOrNull());

            if (result != null) {
                throw new NegocioException("Já existe template cadastrado com esse nome");
            }
        }

        entity = this.templateWhatsAssembler.toEntityUpdate(requestDTO, entity);

        entity = this.templateWhatsService.save(entity);

        TemplateWhatsResponseDTO responseDTO = this.templateWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        TemplateWhats entity = this.templateWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado");
        }

        templateWhatsService.deleteLogical(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
