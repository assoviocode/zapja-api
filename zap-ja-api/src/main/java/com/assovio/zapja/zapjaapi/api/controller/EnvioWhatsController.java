package com.assovio.zapja.zapjaapi.api.controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.zapja.zapjaapi.api.assembler.EnvioWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.EnvioWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.request.EnvioWhatsUpdateRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.EnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.EnvioWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.service.ClienteService;
import com.assovio.zapja.zapjaapi.domain.service.ContatoService;
import com.assovio.zapja.zapjaapi.domain.service.EnvioWhatsService;
import com.assovio.zapja.zapjaapi.domain.service.TemplateWhatsService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("enviosWhats")
public class EnvioWhatsController {

    private EnvioWhatsService envioWhatsService;
    private TemplateWhatsService templateWhatsService;
    private ContatoService contatoService;
    private ClienteService clienteService;

    private EnvioWhatsAssembler envioWhatsAssembler;

    @GetMapping
    public ResponseEntity<Page<EnvioWhatsResponseSimpleDTO>> index(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "nome_contato", required = false) String nomeContato,
            @RequestParam(name = "numero_whatsapp", required = false) String numeroWhatsapp,
            @RequestParam(required = false) EnumStatusEnvioWhats status,
            @RequestParam(name = "celular_origem", required = false) String celularOrigem,
            @RequestParam(name = "template_whats_uuid", required = false) String templateWhatsUuid,
            @RequestParam(name = "contato_uuid", required = false) String contatoUuid) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<EnvioWhats> result = this.envioWhatsService.getByFilters(
                nomeContato,
                numeroWhatsapp,
                status,
                celularOrigem,
                templateWhatsUuid,
                contatoUuid,
                usuarioLogado.getClienteIdOrNull(),
                paginacao);

        Page<EnvioWhatsResponseSimpleDTO> responseDTOs = this.envioWhatsAssembler.toPageDTO(result);

        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EnvioWhatsResponseDTO> show(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        EnvioWhats result = this.envioWhatsService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado!");
        }

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<List<EnvioWhatsResponseDTO>> store(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @Valid @RequestBody EnvioWhatsRequestDTO requestDTO) {

        List<EnvioWhatsResponseDTO> responseDTOs = new ArrayList<EnvioWhatsResponseDTO>();

        TemplateWhats templateWhats = this.templateWhatsService.getByUuidAndCliente(requestDTO.getTemplateWhatsUuid(),
                usuarioLogado.getClienteIdOrNull());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        // for (Long contatoId : requestDTO.getContatosId()) {

        // Contato contato = this.contatoService.getById(contatoId);

        // if (contato != null) {
        // EnvioWhats result = this.envioWhatsAssembler.toEntity(requestDTO);

        // result.setTemplateWhats(templateWhats);
        // result.setContato(contato);
        // result.setStatus(EnumStatusEnvioWhats.NA_FILA);

        // result = this.envioWhatsService.save(result);

        // EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

        // responseDTOs.add(responseDTO);
        // }

        // }

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioWhatsResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody EnvioWhatsUpdateRequestDTO requestDTO) {

        EnvioWhats result = this.envioWhatsService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado!");
        }

        result.setStatus(requestDTO.getStatus());
        result.setLog(requestDTO.getLog());

        if (result.getStatus().equals(EnumStatusEnvioWhats.ENVIADO)) {
            result.setDataReal(OffsetDateTime.now());
        } else if (result.getStatus().equals(EnumStatusEnvioWhats.CANCELADO)) {
            if (requestDTO.getLog() == null) {
                throw new NegocioException("Envie um Log para cancelar o envio!");
            }
        }

        result = this.envioWhatsService.save(result);

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("/cancelar")
    public ResponseEntity<?> updateCancelarLote(@RequestBody List<Long> enviosWhatsId) {

        for (Long id : enviosWhatsId) {

            EnvioWhats resultNaoEditado = this.envioWhatsService.getById((Long) id);

            if (resultNaoEditado != null) {
                resultNaoEditado.setStatus(EnumStatusEnvioWhats.CANCELADO);
                this.envioWhatsService.save(resultNaoEditado);
            }

        }

        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}/enviado")
    public ResponseEntity<?> updateEnviado(@PathVariable Long id) {

        EnvioWhats result = this.envioWhatsService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado!");
        }

        result.setStatus(EnumStatusEnvioWhats.ENVIADO);

        result = this.envioWhatsService.save(result);

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {

        EnvioWhats result = this.envioWhatsService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado");
        }

        envioWhatsService.deleteLogical(result);

        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/proximo")
    // public ResponseEntity<EnvioWhatsResponseDTO> getProximo() {

    // Cliente cliente = this.clienteService.getByIdAndStatus(Long.parseLong("1"),
    // EnumStatusRoboCliente.ENVIANDO);

    // if (cliente == null) {
    // throw new EntidadeNaoEncontradaException("Bot do cliente não foi
    // encontrado!");
    // }

    // EnvioWhats result = this.envioWhatsService.getProximo();

    // if (result == null) {
    // throw new EntidadeNaoEncontradaException("Sem envios novos!");
    // }

    // EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

    // return ResponseEntity.ok(responseDTO);

    // }

}
