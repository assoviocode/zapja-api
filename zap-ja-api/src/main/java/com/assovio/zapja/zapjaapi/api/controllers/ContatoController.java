package com.assovio.zapja.zapjaapi.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.assovio.zapja.zapjaapi.api.assemblers.ContatoAssembler;
import com.assovio.zapja.zapjaapi.api.assemblers.ContatoCampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.ContatoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.models.Contato;
import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.services.ContatoCampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.services.ContatoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("contatos")
public class ContatoController {

    private ContatoService contatoService;
    private ContatoAssembler contatoAssembler;
    private ContatoCampoCustomizadoService contatoCampoCustomizadoService;
    private ContatoCampoCustomizadoAssembler contatoCampoCustomizadoAssembler;

    @GetMapping
    public ResponseEntity<Page<ContatoResponseSimpleDTO>> index(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1000") Integer size,
            @RequestParam(name = "numero_whats", required = false) String numeroWhats,
            @RequestParam(name = "nome", required = false) String nome) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<Contato> result = this.contatoService.getByFilters(numeroWhats, nome, paginacao);

        Page<ContatoResponseSimpleDTO> responseDTOs = this.contatoAssembler.toPageDTO(result);

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

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(result);

        if (requestDTO.getCampoCustomizadoRequestList() != null
                && !requestDTO.getCampoCustomizadoRequestList().isEmpty()) {
            for (ContatoCampoCustomizadoRequestDTO contatoCampoCustomizadoRequestDTO : requestDTO
                    .getCampoCustomizadoRequestList()) {

                ContatoCampoCustomizado contatoCampoCustomizado = new ContatoCampoCustomizado();
                contatoCampoCustomizado = this.contatoCampoCustomizadoAssembler
                        .toEntity(contatoCampoCustomizadoRequestDTO, contatoCampoCustomizado);
                contatoCampoCustomizado.setContato(result);

                contatoCampoCustomizado = this.contatoCampoCustomizadoService.save(contatoCampoCustomizado);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody ContatoRequestDTO requestDTO) {

        Contato contato = this.contatoService.getById(id);

        if (contato == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        if (!contato.getNumeroWhats().equalsIgnoreCase(requestDTO.getNumeroWhats())) {
            Contato result = this.contatoService.getByNumeroWhats(requestDTO.getNumeroWhats());

            if (result != null) {
                throw new NegocioException("Já existe um contato cadastrado com esse numero");
            }
        }

        contato = this.contatoAssembler.toEntity(requestDTO, contato);

        contato = this.contatoService.save(contato);

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(contato);

        if (requestDTO.getCampoCustomizadoRequestList() != null && !requestDTO.getCampoCustomizadoRequestList().isEmpty()) {

            for (ContatoCampoCustomizadoRequestDTO contatoCampoCustomizadoRequestDTO : requestDTO.getCampoCustomizadoRequestList()) {

                ContatoCampoCustomizado contatoCampoCustomizado;

                if (contatoCampoCustomizadoRequestDTO.getId() != null) {

                    contatoCampoCustomizado = this.contatoCampoCustomizadoService.getById(contatoCampoCustomizadoRequestDTO.getId());

                }else{
                    contatoCampoCustomizado = new ContatoCampoCustomizado();
                }

                if (contatoCampoCustomizado != null) {

                    contatoCampoCustomizado = this.contatoCampoCustomizadoAssembler.toEntity(contatoCampoCustomizadoRequestDTO, contatoCampoCustomizado);

                    contatoCampoCustomizado = this.contatoCampoCustomizadoService.save(contatoCampoCustomizado);

                    ContatoCampoCustomizadoResponseDTO contatoCampoCustomizadoResponseDTO = this.contatoCampoCustomizadoAssembler.toDTO(contatoCampoCustomizado);
                    
                    responseDTO.getCampoCustomizadoResponseDTOs().add(contatoCampoCustomizadoResponseDTO);
                }

            }
        }

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
