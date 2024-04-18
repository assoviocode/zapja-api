package com.assovio.zapja.zapjaapi.api.controllers;

import com.assovio.zapja.zapjaapi.api.assemblers.EnvioWhatsAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.EnvioWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.request.EnvioWhatsUpdateRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.EnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.EnvioWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.exceptions.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exceptions.NegocioException;
import com.assovio.zapja.zapjaapi.domain.models.Cliente;
import com.assovio.zapja.zapjaapi.domain.models.Contato;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusBotCliente;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.TemplateWhats;
import com.assovio.zapja.zapjaapi.domain.services.ClienteService;
import com.assovio.zapja.zapjaapi.domain.services.ContatoService;
import com.assovio.zapja.zapjaapi.domain.services.EnvioWhatsService;
import com.assovio.zapja.zapjaapi.domain.services.TemplateWhatsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("enviosWhats")
public class EnvioWhatsController {

    private EnvioWhatsService envioWhatsService;
    private EnvioWhatsAssembler envioWhatsAssembler;
    private TemplateWhatsService templateWhatsService;
    private ContatoService contatoService;
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<EnvioWhatsResponseSimpleDTO>> index(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "30") Integer size,
            @RequestParam(name = "nome_contato", required = false) String nomeContato,
            @RequestParam(name = "numero_whatsapp", required = false) String numeroWhatsapp,
            @RequestParam(name = "status", required = false) EnumStatusEnvioWhats status,
            @RequestParam(name = "celular_origem", required = false) String celularOrigem,
            @RequestParam(name = "template_whats_id", required = false) Long templateWhatsId,
            @RequestParam(name = "contato_id", required = false) Long contatoId,
            @RequestParam(name = "data_prevista", required = false) Date dataPrevista) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<EnvioWhats> result = this.envioWhatsService.getByFilters(
                nomeContato,
                numeroWhatsapp,
                status,
                celularOrigem,
                templateWhatsId,
                contatoId,
                dataPrevista,
                paginacao);

        Page<EnvioWhatsResponseSimpleDTO> responseDTOs = this.envioWhatsAssembler.toPageDTO(result);

        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioWhatsResponseDTO> show(@PathVariable Long id) {

        EnvioWhats result = this.envioWhatsService.getById(id);

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Envio não encontrado!");
        }

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<List<EnvioWhatsResponseDTO>> store(@Valid @RequestBody EnvioWhatsRequestDTO requestDTO) {

        List<EnvioWhatsResponseDTO> responseDTOs = new ArrayList<EnvioWhatsResponseDTO>();

        TemplateWhats templateWhats = this.templateWhatsService.getById(requestDTO.getTemplateWhatsId());

        if (templateWhats == null) {
            throw new EntidadeNaoEncontradaException("Template não encontrado!");
        }

        for (Long contatoId : requestDTO.getContatosId()) {

            Contato contato = this.contatoService.getById(contatoId);

            if (contato != null) {
                EnvioWhats result = this.envioWhatsAssembler.toEntity(requestDTO);

                result.setTemplateWhats(templateWhats);
                result.setContato(contato);
                result.setStatus(EnumStatusEnvioWhats.NA_FILA);

                result = this.envioWhatsService.save(result);

                EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

                responseDTOs.add(responseDTO);
            }

        }

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
            result.setDataReal(new Date());
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

    @GetMapping("/proximo")
    public ResponseEntity<EnvioWhatsResponseDTO> getProximo() {

        Cliente cliente = this.clienteService.getByIdAndStatus(Long.parseLong("1"), EnumStatusBotCliente.ENVIANDO);

        if (cliente == null) {
            throw new EntidadeNaoEncontradaException("Bot do cliente não foi encontrado!");
        }

        EnvioWhats result = this.envioWhatsService.getProximo();

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Sem envios novos!");
        }

        EnvioWhatsResponseDTO responseDTO = this.envioWhatsAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);

    }

}
