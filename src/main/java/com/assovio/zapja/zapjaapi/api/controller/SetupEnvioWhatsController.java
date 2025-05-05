package com.assovio.zapja.zapjaapi.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.EnvioWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.assembler.SetupEnvioWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.SetupEnvioWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.SetupEnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.model.Contato;
import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.SetupEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.service.ContatoService;
import com.assovio.zapja.zapjaapi.domain.service.SetupEnvioWhatsService;
import com.assovio.zapja.zapjaapi.domain.service.TemplateWhatsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("setupsEnvioWhats")
public class SetupEnvioWhatsController {

    private final SetupEnvioWhatsService setupEnvioWhatsService;
    private final ContatoService contatoService;
    private final TemplateWhatsService templateWhatsService;

    private final SetupEnvioWhatsAssembler setupEnvioWhatsAssembler;
    private final EnvioWhatsAssembler envioWhatsAssembler;

    @PostMapping
    public ResponseEntity<SetupEnvioWhatsResponseDTO> store(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @Valid @RequestBody SetupEnvioWhatsRequestDTO requestDTO) {

        SetupEnvioWhats entity = this.setupEnvioWhatsAssembler.toEntity(requestDTO);
        entity.setCliente(usuarioLogado.getCliente());

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(
                requestDTO.getEnvioWhatsBase().getTemplateWhatsUuid(), usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado");
        }

        for (String contatoUuid : requestDTO.getEnvioWhatsBase().getContatosUuid()) {

            Contato contato = this.contatoService.getByUuidAndCliente(contatoUuid, usuarioLogado.getClienteIdOrNull());

            if (contato != null) {
                EnvioWhats envioWhats = this.envioWhatsAssembler.toEntity(requestDTO.getEnvioWhatsBase());

                envioWhats.setStatus(EnumStatusEnvioWhats.NA_FILA);
                envioWhats.setContato(contato);
                envioWhats.setTemplateWhats(templateWhats);
                envioWhats.setSetupEnvioWhats(entity);
                envioWhats.setCliente(usuarioLogado.getCliente());

                entity.getEnviosWhats().add(envioWhats);
            }
        }

        entity = this.setupEnvioWhatsService.save(entity);

        SetupEnvioWhatsResponseDTO responseDTO = this.setupEnvioWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<SetupEnvioWhatsResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid,
            @Valid @RequestBody SetupEnvioWhatsRequestDTO requestDTO) {

        SetupEnvioWhats entity = this.setupEnvioWhatsService.getByUuidAndCliente(uuid,
                usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Setup não encontrado");
        }

        entity = this.setupEnvioWhatsAssembler.toEntityUpdate(requestDTO, entity);

        entity = this.setupEnvioWhatsService.save(entity);

        SetupEnvioWhatsResponseDTO responseDTO = this.setupEnvioWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        SetupEnvioWhats entity = this.setupEnvioWhatsService.getByUuidAndCliente(uuid,
                usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Setup não encontrado");
        }

        this.setupEnvioWhatsService.deleteLogical(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
