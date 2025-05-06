package com.assovio.zapja.zapjaapi.api.controller;

import java.util.List;

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

import com.assovio.zapja.zapjaapi.api.assembler.CampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.CampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.CampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.model.TipoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.CampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.service.TipoCampoCustomizadoService;

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
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(required = false) String rotulo,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Boolean obrigatorio,
            @RequestParam(name = "tipo_campo_customizado_uuid", required = false) String tipoCampoCustomizadoUuid) {

        List<CampoCustomizado> result = this.campoCustomizadoService.getByFilters(rotulo, ativo, obrigatorio,
                tipoCampoCustomizadoUuid, usuarioLogado.getClienteIdOrNull());

        List<CampoCustomizadoResponseDTO> responseDTOs = this.campoCustomizadoAssembler.toCollectionDTO(result);

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CampoCustomizadoResponseDTO> show(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        CampoCustomizado result = this.campoCustomizadoService.getByUuidAndCliente(uuid,
                usuarioLogado.getClienteIdOrNull());

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado!");
        }

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(result);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CampoCustomizadoResponseDTO> store(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @Valid @RequestBody CampoCustomizadoRequestDTO requestDTO) {

        TipoCampoCustomizado tipoCampoCustomizado = this.tipoCampoCustomizadoService
                .getByUuidAndCliente(requestDTO.getTipoCampoCustomizadoUuid(), usuarioLogado.getClienteIdOrNull());

        if (tipoCampoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Tipo Campo Customizado não encontrado");
        }

        // MAU CHEIRO, AJUSTAR DEPOIS // ESTUDAR MODELMAPPER // PROBLEMA COM MODELMAPPER
        CampoCustomizado campoCustomizado = new CampoCustomizado();
        campoCustomizado.setRotulo(requestDTO.getRotulo());
        campoCustomizado.setAtivo(true);
        campoCustomizado.setObrigatorio(requestDTO.getObrigatorio());
        campoCustomizado.setTipoCampoCustomizado(tipoCampoCustomizado);
        campoCustomizado.setCliente(usuarioLogado.getCliente());

        campoCustomizado = this.campoCustomizadoService.save(campoCustomizado);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(campoCustomizado);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CampoCustomizadoResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid,
            @Valid @RequestBody CampoCustomizadoRequestDTO requestDTO) {

        CampoCustomizado campoCustomizado = this.campoCustomizadoService.getByUuidAndCliente(uuid,
                usuarioLogado.getClienteIdOrNull());

        if (campoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado");
        }

        TipoCampoCustomizado tipoCampoCustomizado = this.tipoCampoCustomizadoService
                .getByUuidAndCliente(requestDTO.getTipoCampoCustomizadoUuid(), usuarioLogado.getClienteIdOrNull());

        if (tipoCampoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Tipo Campo Customizado não encontrado");
        }

        // MAU CHEIRO, AJUSTAR DEPOIS // ESTUDAR MODELMAPPER // PROBLEMA COM MODELMAPPER
        campoCustomizado.setRotulo(requestDTO.getRotulo());
        campoCustomizado.setObrigatorio(requestDTO.getObrigatorio());
        campoCustomizado.setTipoCampoCustomizado(tipoCampoCustomizado);
        campoCustomizado.setCliente(usuarioLogado.getCliente());

        campoCustomizado = this.campoCustomizadoService.save(campoCustomizado);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(campoCustomizado);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        CampoCustomizado result = this.campoCustomizadoService.getByUuidAndCliente(uuid,
                usuarioLogado.getClienteIdOrNull());

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado");
        }

        campoCustomizadoService.deleteLogical(result);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{uuid}/desativar")
    public ResponseEntity<CampoCustomizadoResponseDTO> updateDesativar(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        CampoCustomizado campoCustomizado = this.campoCustomizadoService.getByUuidAndCliente(uuid,
                usuarioLogado.getClienteIdOrNull());

        if (campoCustomizado == null) {
            throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado");
        }

        campoCustomizado.setAtivo(false);

        campoCustomizado = this.campoCustomizadoService.save(campoCustomizado);

        CampoCustomizadoResponseDTO responseDTO = this.campoCustomizadoAssembler.toDTO(campoCustomizado);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
