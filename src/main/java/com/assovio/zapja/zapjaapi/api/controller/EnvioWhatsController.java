package com.assovio.zapja.zapjaapi.api.controller;

import java.time.OffsetDateTime;
import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.EnvioWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.EnvioWhatsUpdateRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.EnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.EnvioWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exception.NegocioException;
import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.service.EnvioWhatsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("enviosWhats")
public class EnvioWhatsController {

    private final EnvioWhatsService envioWhatsService;

    private final EnvioWhatsAssembler envioWhatsAssembler;

    @GetMapping
    public ResponseEntity<Page<EnvioWhatsResponseSimpleDTO>> index(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "nome_contato", required = false) String nomeContato,
            @RequestParam(name = "celular_destino", required = false) String celularDestino,
            @RequestParam(required = false) EnumStatusEnvioWhats status,
            @RequestParam(name = "celular_origem", required = false) String celularOrigem,
            @RequestParam(name = "template_whats_uuid", required = false) String templateWhatsUuid,
            @RequestParam(name = "contato_uuid", required = false) String contatoUuid) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<EnvioWhats> entity = this.envioWhatsService.getByFilters(
                nomeContato,
                celularDestino,
                status,
                celularOrigem,
                templateWhatsUuid,
                contatoUuid,
                usuarioLogado.getClienteIdOrNull(),
                paginacao);

        Page<EnvioWhatsResponseSimpleDTO> responseDTOs = this.envioWhatsAssembler.toPageDTO(entity);

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EnvioWhatsResponseDTO> show(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        EnvioWhats entity = this.envioWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado!");
        }

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EnvioWhatsResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid,
            @Valid @RequestBody EnvioWhatsUpdateRequestDTO requestDTO) {

        EnvioWhats entity = this.envioWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado!");
        }

        entity.setStatus(requestDTO.getStatus());
        entity.setLog(requestDTO.getLog());

        if (entity.getStatus().equals(EnumStatusEnvioWhats.ENVIADO)) {
            entity.setDataReal(OffsetDateTime.now());
        } else if (entity.getStatus().equals(EnumStatusEnvioWhats.CANCELADO)) {
            if (requestDTO.getLog() == null) {
                throw new NegocioException("Envie um Log para cancelar o envio!");
            }
        }

        entity = this.envioWhatsService.save(entity);

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/cancelar")
    public ResponseEntity<?> updateCancelarLote(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestBody List<String> enviosWhatsUuid) {

        for (String uuid : enviosWhatsUuid) {

            EnvioWhats entity = this.envioWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

            if (entity != null) {
                entity.setStatus(EnumStatusEnvioWhats.CANCELADO);
                this.envioWhatsService.save(entity);
            }

        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}/enviado")
    public ResponseEntity<?> updateEnviado(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        EnvioWhats entity = this.envioWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado!");
        }

        entity.setStatus(EnumStatusEnvioWhats.ENVIADO);
        entity.setDataReal(OffsetDateTime.now());

        entity = this.envioWhatsService.save(entity);

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        EnvioWhats entity = this.envioWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado");
        }

        envioWhatsService.deleteLogical(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
