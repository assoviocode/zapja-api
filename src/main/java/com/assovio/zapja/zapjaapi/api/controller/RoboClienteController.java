package com.assovio.zapja.zapjaapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.EnvioWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.assembler.RoboClienteAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.response.EnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.RoboClienteResponseDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exception.NegocioException;
import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.RoboCliente;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusRoboCliente;
import com.assovio.zapja.zapjaapi.domain.service.EnvioWhatsService;
import com.assovio.zapja.zapjaapi.domain.service.RoboClienteService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("robosCliente")
public class RoboClienteController {

    private final RoboClienteService roboClienteService;
    private final EnvioWhatsService envioWhatsService;

    private final RoboClienteAssembler roboClienteAssembler;
    private final EnvioWhatsAssembler envioWhatsAssembler;

    @GetMapping
    public ResponseEntity<List<RoboClienteResponseDTO>> index(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(name = "celular_origem", required = false) String celularOrigem,
            @RequestParam(required = false) EnumStatusRoboCliente status) {

        List<RoboCliente> result = this.roboClienteService.getByFilters(celularOrigem, status,
                usuarioLogado.getClienteIdOrNull());

        List<RoboClienteResponseDTO> responseDTOs = this.roboClienteAssembler.toCollectionDTO(result);

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{uuid}/proximo")
    public ResponseEntity<EnvioWhatsResponseDTO> getProximo(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        RoboCliente roboCliente = this.roboClienteService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (roboCliente == null) {
            throw new EntidadeNaoEncontradaException("Robo do Cliente não foi encontrado!");
        }

        if (roboCliente.getStatus().equals(EnumStatusRoboCliente.PARADO)) {
            throw new NegocioException("Robô pausado");
        }

        EnvioWhats entity = this.envioWhatsService.getProximo(roboCliente.getCelularOrigem(),
                usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Sem envios novos!");
        }

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(entity);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/iniciarTodos")
    public ResponseEntity<HttpStatus> iniciarTodos(@AuthenticationPrincipal Usuario usuarioLogado) {

        List<RoboCliente> result = this.roboClienteService.getByFilters(null, EnumStatusRoboCliente.PARADO,
                usuarioLogado.getClienteIdOrNull());

        result.stream().forEach(rb -> rb.setStatus(EnumStatusRoboCliente.ENVIANDO));

        this.roboClienteService.saveAll(result);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/pararTodos")
    public ResponseEntity<HttpStatus> pararTodos(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<RoboCliente> result = this.roboClienteService.getByFilters(null, EnumStatusRoboCliente.ENVIANDO,
                usuarioLogado.getClienteIdOrNull());

        result.stream().forEach(rb -> rb.setStatus(EnumStatusRoboCliente.PARADO));

        this.roboClienteService.saveAll(result);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{uuid}/iniciarEnvio")
    public ResponseEntity<HttpStatus> iniciarEnvio(@PathVariable String uuid,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        RoboCliente roboCliente = this.roboClienteService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (roboCliente == null) {
            throw new EntidadeNaoEncontradaException("Robo do Cliente não foi encontrado!");
        }

        roboCliente.setStatus(EnumStatusRoboCliente.ENVIANDO);

        roboCliente = this.roboClienteService.save(roboCliente);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{uuid}/pararEnvio")
    public ResponseEntity<HttpStatus> pararEnvio(@PathVariable String uuid,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        RoboCliente roboCliente = this.roboClienteService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (roboCliente == null) {
            throw new EntidadeNaoEncontradaException("Robo do Cliente não foi encontrado!");
        }

        roboCliente.setStatus(EnumStatusRoboCliente.PARADO);

        roboCliente = this.roboClienteService.save(roboCliente);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
