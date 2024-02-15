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
import com.assovio.zapja.zapjaapi.domain.models.TipoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.services.CampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.services.TipoCampoCustomizadoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("camposCustomizado")
public class CampoCustomizadoController {

    private CampoCustomizadoService campoCustomizadoService;
    private CampoCustomizadoAssembler campoCustomizadoAssembler;
    private TipoCampoCustomizadoService tipoCampoCustomizadoService;

    @GetMapping
    public ResponseEntity<List<CampoCustomizadoResponseDTO>> index(
            @RequestParam(name = "rotulo", required = false) String rotulo,
            @RequestParam(name = "ativo", required = false) Boolean ativo,
            @RequestParam(name = "obrigatorio", required = false) Boolean obrigatorio,
            @RequestParam(name = "tipo_campo_customizado_id", required = false) Long tipoCampoCustomizadoId) {

        List<CampoCustomizado> result = this.campoCustomizadoService.getByFilters(rotulo, ativo, obrigatorio,
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

        TipoCampoCustomizado tipoCampoCustomizado = this.tipoCampoCustomizadoService
                .getById(requestDTO.getTipoCampoCustomizadoId());

        if (tipoCampoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Tipo Campo Customizado não encontrado");
        }

        // MAU CHEIRO, AJUSTAR DEPOIS // ESTUDAR MODELMAPPER // PROBLEMA COM MODELMAPPER
        CampoCustomizado campoCustomizado = new CampoCustomizado();
        campoCustomizado.setRotulo(requestDTO.getRotulo());
        campoCustomizado.setTipoCampoCustomizado(tipoCampoCustomizado);
        campoCustomizado.setAtivo(true);
        campoCustomizado.setObrigatorio(requestDTO.getObrigatorio());

        campoCustomizado = this.campoCustomizadoService.save(campoCustomizado);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(campoCustomizado);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampoCustomizadoResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody CampoCustomizadoRequestDTO requestDTO) {

        CampoCustomizado campoCustomizado = this.campoCustomizadoService.getById(id);

        if (campoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado");
        }

        TipoCampoCustomizado tipoCampoCustomizado = this.tipoCampoCustomizadoService
                .getById(requestDTO.getTipoCampoCustomizadoId());

        if (tipoCampoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Tipo Campo Customizado não encontrado");
        }

        // MAU CHEIRO, AJUSTAR DEPOIS // ESTUDAR MODELMAPPER // PROBLEMA COM MODELMAPPER
        campoCustomizado.setRotulo(requestDTO.getRotulo());
        campoCustomizado.setTipoCampoCustomizado(tipoCampoCustomizado);
        campoCustomizado.setObrigatorio(requestDTO.getObrigatorio());

        campoCustomizado = this.campoCustomizadoService.save(campoCustomizado);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(campoCustomizado);

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

    @PutMapping("/{id}/desativar")
    public ResponseEntity<CampoCustomizadoResponseDTO> updateDesativar(@PathVariable Long id) {

        CampoCustomizado campoCustomizado = this.campoCustomizadoService.getById(id);

        if (campoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado");
        }

        campoCustomizado.setAtivo(false);

        campoCustomizado = this.campoCustomizadoService.save(campoCustomizado);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(campoCustomizado);

        return ResponseEntity.ok(responseDTO);

    }

}
