package com.assovio.zapja.zapjaapi.api.controllers;

import java.util.List;

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

import com.assovio.zapja.zapjaapi.api.assemblers.TipoCampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.TipoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.TipoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.models.TipoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.services.TipoCampoCustomizadoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("tiposCampoCustomizado")
public class TipoCampoCustomizadoController {

    private TipoCampoCustomizadoService tipoCampoCustomizadoService;
    private TipoCampoCustomizadoAssembler tipoCampoCustomizadoAssembler;

    @GetMapping
    public ResponseEntity<List<TipoCampoCustomizadoResponseDTO>> index(
            @RequestParam(name = "nome", required = false) String nome) {

        List<TipoCampoCustomizado> result = this.tipoCampoCustomizadoService.getByFilters(nome);

        List<TipoCampoCustomizadoResponseDTO> responseDTOs = this.tipoCampoCustomizadoAssembler.toCollectionDTO(result);

        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCampoCustomizadoResponseDTO> show(@PathVariable Long id) {

        TipoCampoCustomizado result = this.tipoCampoCustomizadoService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Tipo de Campo Customizado não encontrado!");
        }

        TipoCampoCustomizadoResponseDTO responseDTO = this.tipoCampoCustomizadoAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<TipoCampoCustomizadoResponseDTO> store(
            @Valid @RequestBody TipoCampoCustomizadoRequestDTO requestDTO) {

        TipoCampoCustomizado result = this.tipoCampoCustomizadoService.getByNome(requestDTO.getNome());

        if (result != null) {
            throw new NegocioException("Já existe tipo cadastrado com esse nome");
        }

        result = this.tipoCampoCustomizadoAssembler.toEntity(requestDTO);

        result = this.tipoCampoCustomizadoService.save(result);

        TipoCampoCustomizadoResponseDTO responseDTO = this.tipoCampoCustomizadoAssembler.toDTO(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCampoCustomizadoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody TipoCampoCustomizadoRequestDTO requestDTO) {

        TipoCampoCustomizado tipoCampoCustomizado = this.tipoCampoCustomizadoService.getById(id);

        if (tipoCampoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Tipo de campo customizado não encontrado");
        }

        if (!tipoCampoCustomizado.getNome().equalsIgnoreCase(requestDTO.getNome())) {
            TipoCampoCustomizado result = this.tipoCampoCustomizadoService.getByNome(requestDTO.getNome());

            if (result != null) {
                throw new NegocioException("Já existe tipo cadastrado com esse nome");
            }
        }

        tipoCampoCustomizado = this.tipoCampoCustomizadoAssembler.toEntity(requestDTO, tipoCampoCustomizado);
        tipoCampoCustomizado.setId(id);

        tipoCampoCustomizado = this.tipoCampoCustomizadoService.save(tipoCampoCustomizado);

        TipoCampoCustomizadoResponseDTO responseDTO = this.tipoCampoCustomizadoAssembler.toDTO(tipoCampoCustomizado);
        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {

        TipoCampoCustomizado result = this.tipoCampoCustomizadoService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Tipo de campo customizado não encontrado");
        }

        tipoCampoCustomizadoService.delete(result);

        return ResponseEntity.noContent().build();
    }

}
