package com.assovio.zapja.zapjaapi.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assemblers.TemplateWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.TemplateWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.TemplateWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.models.TemplateWhats;
import com.assovio.zapja.zapjaapi.domain.services.TemplateWhatsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("templatesWhats")
public class TemplateWhatsController {

    private TemplateWhatsService templateWhatsService;
    private TemplateWhatsAssembler templateWhatsAssembler;

    @GetMapping
    public ResponseEntity<Page<TemplateWhatsResponseDTO>> index(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "nome", required = false) String nome) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<TemplateWhats> result = this.templateWhatsService.getByFilters(nome, paginacao);

        Page<TemplateWhatsResponseDTO> responseDTOs = this.templateWhatsAssembler.toPageDTO(result);

        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateWhatsResponseDTO> show(@PathVariable Long id) {

        TemplateWhats result = this.templateWhatsService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        TemplateWhatsResponseDTO responseDTO = this.templateWhatsAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<TemplateWhatsResponseDTO> store(@Valid @RequestBody TemplateWhatsRequestDTO requestDTO) {

        TemplateWhats result = this.templateWhatsService.getByNome(requestDTO.getNome());

        if (result != null) {
            throw new NegocioException("Já existe template cadastrado com esse nome");
        }

        result = this.templateWhatsAssembler.toEntity(requestDTO);
        result.setAtivo(true);

        result = this.templateWhatsService.save(result);

        TemplateWhatsResponseDTO responseDTO = this.templateWhatsAssembler.toDTO(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemplateWhatsResponseDTO> Update(@PathVariable Long id,
            @Valid @RequestBody TemplateWhatsRequestDTO requestDTO) {

        TemplateWhats resultNaoEditado = this.templateWhatsService.getById(id);

        if (resultNaoEditado == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado");
        }

        if (!resultNaoEditado.getNome().equalsIgnoreCase(requestDTO.getNome())) {
            TemplateWhats result = this.templateWhatsService.getByNome(requestDTO.getNome());

            if (result != null) {
                throw new NegocioException("Já existe template cadastrado com esse nome");
            }
        }

        TemplateWhats resultEditado = this.templateWhatsAssembler.toEntity(requestDTO);
        resultEditado.setId(id);
        resultEditado.setCreatedAt(resultNaoEditado.getCreatedAt());

        resultEditado = this.templateWhatsService.save(resultEditado);

        TemplateWhatsResponseDTO responseDTO = this.templateWhatsAssembler.toDTO(resultEditado);
        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Destroy(@PathVariable Long id) {

        TemplateWhats result = this.templateWhatsService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado");
        }

        templateWhatsService.deleteLogical(result);

        return ResponseEntity.noContent().build();
    }

}
