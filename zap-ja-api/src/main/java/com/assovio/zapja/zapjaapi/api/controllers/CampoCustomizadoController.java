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

import com.assovio.zapja.zapjaapi.api.assemblers.CampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.CampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.CampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.models.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.services.CampoCustomizadoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("camposCustomizado")
public class CampoCustomizadoController {

    private CampoCustomizadoService campoCustomizadoService;
    private CampoCustomizadoAssembler campoCustomizadoAssembler;

    @GetMapping
    public ResponseEntity<List<CampoCustomizadoResponseDTO>> index(
            @RequestParam(name = "rotulo", required = false) String rotulo,
            @RequestParam(name = "ativo", required = false) Boolean ativo,
            @RequestParam(name = "tipo_campo_customizado_id", required = false) Long tipoCampoCustomizadoId) {

        List<CampoCustomizado> result = this.campoCustomizadoService.getByFilters(rotulo, ativo,
                tipoCampoCustomizadoId);

        List<CampoCustomizadoResponseDTO> responseDTOs = this.campoCustomizadoAssembler.toCollectionDTO(result);

        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CampoCustomizadoResponseDTO> show(@PathVariable Long id) {

        CampoCustomizado result = this.campoCustomizadoService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado!");
        }

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<CampoCustomizadoResponseDTO> store(
            @Valid @RequestBody CampoCustomizadoRequestDTO requestDTO) {

        CampoCustomizado result = this.campoCustomizadoAssembler.toEntity(requestDTO);

        result = this.campoCustomizadoService.save(result);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampoCustomizadoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody CampoCustomizadoRequestDTO requestDTO) {

        CampoCustomizado resultNaoEditado = this.campoCustomizadoService.getById(id);

        if (resultNaoEditado == null) {
            throw new EntidadeNaoEncontradaException("CampoCustomizado não encontrado");
        }

        CampoCustomizado resultEditado = this.campoCustomizadoAssembler.toEntity(requestDTO);
        resultEditado.setId(id);
        resultEditado.setCreatedAt(resultNaoEditado.getCreatedAt());

        resultEditado = this.campoCustomizadoService.save(resultEditado);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(resultEditado);

        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {

        CampoCustomizado result = this.campoCustomizadoService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("CampoCustomizado não encontrado");
        }

        campoCustomizadoService.deleteLogical(result);

        return ResponseEntity.noContent().build();
    }

}
