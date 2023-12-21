package com.assovio.zapja.zapjaapi.api.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.assovio.zapja.zapjaapi.api.assemblers.ContatoAssembler;
import com.assovio.zapja.zapjaapi.api.assemblers.ContatoCampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.ContatoCampoCustomizadoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.models.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.models.Contato;
import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.services.CampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.services.ContatoCampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.services.ContatoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("contatos")
public class ContatoController {

    private ContatoService contatoService;
    private ContatoAssembler contatoAssembler;
    private CampoCustomizadoService campoCustomizadoService;
    private ContatoCampoCustomizadoService contatoCampoCustomizadoService;
    private ContatoCampoCustomizadoAssembler contatoCampoCustomizadoAssembler;

    @GetMapping
    public ResponseEntity<Page<ContatoResponseDTO>> index(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "numero_whats", required = false) String numeroWhats,
            @RequestParam(name = "nome", required = false) String nome) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<Contato> result = this.contatoService.getByFilters(numeroWhats, nome, paginacao);

        Page<ContatoResponseDTO> responseDTOs = this.contatoAssembler.toPageDTO(result);

        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoResponseDTO> show(@PathVariable Long id) {

        Contato result = this.contatoService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado!");
        }

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ContatoResponseDTO> store(@Valid @RequestBody ContatoRequestDTO requestDTO) {

        Contato result = this.contatoService.getByNumeroWhats(requestDTO.getNumeroWhats());

        if (result != null) {
            throw new NegocioException("Já existe um contato cadastrado com esse numero");
        }

        result = this.contatoAssembler.toEntity(requestDTO);

        result = this.contatoService.save(result);

        List<ContatoCampoCustomizadoResponseSimpleDTO> responseSimpleDTOs = new ArrayList<>();

        for (ContatoCampoCustomizadoRequestDTO contatoCampoCustomizadoRequestDTO : requestDTO.getCamposCustomizados()) {

            CampoCustomizado campoCustomizado = this.campoCustomizadoService
                    .getById(contatoCampoCustomizadoRequestDTO.getCampoCustomizadoId());

            if (campoCustomizado == null) {
                throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado!");
            }

            ContatoCampoCustomizado contatoCampoCustomizado = this.contatoCampoCustomizadoAssembler
                    .toEntity(contatoCampoCustomizadoRequestDTO);

            contatoCampoCustomizado.setCampoCustomizado(campoCustomizado);
            contatoCampoCustomizado.setContato(result);
            contatoCampoCustomizado = this.contatoCampoCustomizadoService.save(contatoCampoCustomizado);

            ContatoCampoCustomizadoResponseSimpleDTO responseSimpleDTO = this.contatoCampoCustomizadoAssembler
                    .toSimpleDTO(contatoCampoCustomizado);

            responseSimpleDTOs.add(responseSimpleDTO);

        }

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(result);
        responseDTO.setContatoCampoCustomizadoResponseSimpleDTOs(responseSimpleDTOs);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody ContatoRequestDTO requestDTO) {

        Contato resultNaoEditado = this.contatoService.getById(id);

        if (resultNaoEditado == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        if (!resultNaoEditado.getNumeroWhats().equalsIgnoreCase(requestDTO.getNumeroWhats())) {
            Contato result = this.contatoService.getByNumeroWhats(requestDTO.getNumeroWhats());

            if (result != null) {
                throw new NegocioException("Já existe um contato cadastrado com esse numero");
            }
        }

        Contato resultEditado = this.contatoAssembler.toEntity(requestDTO);
        resultEditado.setId(id);
        resultEditado.setCreatedAt(resultNaoEditado.getCreatedAt());

        resultEditado = this.contatoService.save(resultEditado);

        List<ContatoCampoCustomizadoResponseSimpleDTO> responseSimpleDTOs = new ArrayList<>();

        for (ContatoCampoCustomizadoRequestDTO contatoCampoCustomizadoRequestDTO : requestDTO.getCamposCustomizados()) {

            CampoCustomizado campoCustomizado = this.campoCustomizadoService
                    .getById(contatoCampoCustomizadoRequestDTO.getCampoCustomizadoId());

            if (campoCustomizado == null) {
                throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado!");
            }

            ContatoCampoCustomizado contatoCampoCustomizadoNaoEditado = this.contatoCampoCustomizadoService
                    .getByContatoAndCampoCustomizado(resultEditado.getId(), campoCustomizado.getId());

            if (contatoCampoCustomizadoNaoEditado == null) {
                throw new EntidadeNaoEncontradaException("ContatoCampo Customizado não encontrado!");
            }

            ContatoCampoCustomizado contatoCampoCustomizadoEditado = this.contatoCampoCustomizadoAssembler
                    .toEntity(contatoCampoCustomizadoRequestDTO);

            contatoCampoCustomizadoEditado.setId(contatoCampoCustomizadoNaoEditado.getId());
            contatoCampoCustomizadoEditado.setCampoCustomizado(campoCustomizado);
            contatoCampoCustomizadoEditado.setContato(resultEditado);
            contatoCampoCustomizadoEditado = this.contatoCampoCustomizadoService.save(contatoCampoCustomizadoEditado);

            ContatoCampoCustomizadoResponseSimpleDTO responseSimpleDTO = this.contatoCampoCustomizadoAssembler
                    .toSimpleDTO(contatoCampoCustomizadoNaoEditado);

            responseSimpleDTOs.add(responseSimpleDTO);

        }

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(resultEditado);
        responseDTO.setContatoCampoCustomizadoResponseSimpleDTOs(responseSimpleDTOs);

        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {

        Contato result = this.contatoService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        contatoService.deleteLogical(result);

        return ResponseEntity.noContent().build();
    }

}
