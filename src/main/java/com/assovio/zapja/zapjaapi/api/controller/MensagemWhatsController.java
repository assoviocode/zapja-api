package com.assovio.zapja.zapjaapi.api.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.MensagemWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.MensagemWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.MensagemWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.MensagemWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exception.NegocioException;
import com.assovio.zapja.zapjaapi.domain.model.MensagemWhats;
import com.assovio.zapja.zapjaapi.domain.model.Midia;
import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.MensagemWhatsService;
import com.assovio.zapja.zapjaapi.domain.service.MidiaService;
import com.assovio.zapja.zapjaapi.domain.service.TemplateWhatsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("templatesWhats/{templateWhatsUuid}/mensagensWhats")
public class MensagemWhatsController {

    private final TemplateWhatsService templateWhatsService;
    private final MensagemWhatsService mensagemWhatsService;
    private final MidiaService midiaService;
    private final MensagemWhatsAssembler mensagemWhatsAssembler;

    @GetMapping
    public ResponseEntity<Page<MensagemWhatsResponseSimpleDTO>> index(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String templateWhatsUuid,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size,
            @RequestParam(required = false) String texto) {

        Pageable paginacao = PageRequest.of(page, size);

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(templateWhatsUuid,
                usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        Page<MensagemWhats> result = this.mensagemWhatsService.getByFilters(texto, templateWhats.getUuid(),
                usuarioLogado.getClienteIdOrNull(), paginacao);

        Page<MensagemWhatsResponseSimpleDTO> responseDTOs = this.mensagemWhatsAssembler.toPageDTO(result);

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<MensagemWhatsResponseDTO> show(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String templateWhatsUuid,
            @PathVariable String uuid) {

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(templateWhatsUuid,
                usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        MensagemWhats result = this.mensagemWhatsService.getByUuidAndTemplateWhatsAndCliente(uuid,
                templateWhats.getUuid(), usuarioLogado.getClienteIdOrNull());

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Mensagem não encontrada!");
        }

        MensagemWhatsResponseDTO responseDTO = this.mensagemWhatsAssembler.toDTO(result);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensagemWhatsResponseDTO> store(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String templateWhatsUuid,
            @Valid @ModelAttribute MensagemWhatsRequestDTO requestDTO) throws Exception {

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(templateWhatsUuid,
                usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        MensagemWhats entity = null;

        if (requestDTO.getTexto() != null) {
            entity = this.mensagemWhatsService.getByTextoAndTemplateWhatsAndCliente(
                    requestDTO.getTexto(),
                    templateWhats.getUuid(),
                    usuarioLogado.getClienteIdOrNull());

            if (entity != null) {
                throw new NegocioException("Já existe uma mensagem cadastrada com esse texto!");
            }
        }

        entity = this.mensagemWhatsAssembler.toEntity(requestDTO);

        if (requestDTO.getMidia() != null) {
            Midia midia = this.midiaService.saveImage(requestDTO.getMidia());
            entity.setMidia(midia);
        }

        entity.setTemplateWhats(templateWhats);
        entity.setCliente(usuarioLogado.getCliente());
        entity = this.mensagemWhatsService.save(entity);

        MensagemWhatsResponseDTO responseDTO = this.mensagemWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensagemWhatsResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String templateWhatsUuid,
            @PathVariable String uuid,
            @Valid @ModelAttribute MensagemWhatsRequestDTO requestDTO) throws Exception {

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(templateWhatsUuid,
                usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        MensagemWhats entity = this.mensagemWhatsService.getByUuidAndTemplateWhatsAndCliente(uuid,
                templateWhats.getUuid(), usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Mensagem não encontrada!");
        }

        if (requestDTO.getTexto() != null && !entity.getTexto().equalsIgnoreCase(requestDTO.getTexto())) {
            MensagemWhats result = this.mensagemWhatsService.getByTextoAndTemplateWhatsAndCliente(
                    requestDTO.getTexto(),
                    templateWhats.getUuid(),
                    usuarioLogado.getClienteIdOrNull());

            if (result != null) {
                throw new NegocioException("Já existe uma mensagem cadastrada com esse texto!");
            }
        }

        entity = this.mensagemWhatsAssembler.toEntityUpdate(requestDTO, entity);

        Midia midiaAnterior = entity.getMidia();

        if (requestDTO.getMidia() != null) {
            Midia midia = this.midiaService.saveImage(requestDTO.getMidia());

            entity.setMidia(midia);
        }

        entity = this.mensagemWhatsService.save(entity);

        if (midiaAnterior != null) {
            this.midiaService.delete(midiaAnterior);
        }

        MensagemWhatsResponseDTO responseDTO = this.mensagemWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String templateWhatsUuid,
            @PathVariable String uuid) {

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(templateWhatsUuid,
                usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        MensagemWhats entity = this.mensagemWhatsService.getByUuidAndTemplateWhatsAndCliente(uuid,
                templateWhats.getUuid(), usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Mensagem não encontrada!");
        }

        mensagemWhatsService.deleteLogical(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{uuid}/midia/download")
    public ResponseEntity<byte[]> getMidia(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String templateWhatsUuid,
            @PathVariable String uuid) throws IOException {

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(templateWhatsUuid,
                usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        MensagemWhats entity = this.mensagemWhatsService.getByUuidAndTemplateWhatsAndCliente(uuid,
                templateWhats.getUuid(), usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Mensagem não encontrada!");
        }

        if (entity.getMidia() != null) {
            Midia midia = this.midiaService.getById(entity.getMidia().getId());

            if (midia != null) {
                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(midia.getTipo()));

                return new ResponseEntity<byte[]>(midia.getArquivo(), headers, HttpStatus.OK);
            }

        }

        return null;
    }
}
