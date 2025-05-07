package com.assovio.zapja.zapjaapi.api.controller;

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

import com.assovio.zapja.zapjaapi.api.assembler.ContatoAssembler;
import com.assovio.zapja.zapjaapi.api.assembler.ContatoCampoCustomizadoAssembler;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.ContatoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.exception.EntidadeNaoEncontradaException;
import com.assovio.zapja.zapjaapi.domain.exception.NegocioException;
import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.model.Contato;
import com.assovio.zapja.zapjaapi.domain.model.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;
import com.assovio.zapja.zapjaapi.domain.service.CampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.service.ContatoCampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.service.ContatoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("contatos")
public class ContatoController {

    private ContatoService contatoService;
    private ContatoAssembler contatoAssembler;
    private ContatoCampoCustomizadoService contatoCampoCustomizadoService;
    private final CampoCustomizadoService campoCustomizadoService;
    private ContatoCampoCustomizadoAssembler contatoCampoCustomizadoAssembler;

    @GetMapping
    public ResponseEntity<Page<ContatoResponseSimpleDTO>> index(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "1000") Integer size,
            @RequestParam(name = "numero_whats", required = false) String numeroWhats,
            @RequestParam(required = false) String nome) {

        Pageable paginacao = PageRequest.of(page, size);

        Page<Contato> result = this.contatoService.getByFilters(numeroWhats, nome, usuarioLogado.getClienteIdOrNull(),
                paginacao);

        Page<ContatoResponseSimpleDTO> responseDTOs = this.contatoAssembler.toPageDTO(result);

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContatoResponseDTO> show(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        Contato result = this.contatoService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (result == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado!");
        }

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(result);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ContatoResponseDTO> store(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @Valid @RequestBody ContatoRequestDTO requestDTO) {

        Contato entity = this.contatoService.getByNumeroWhatsAndCliente(requestDTO.getNumeroWhats(),
                usuarioLogado.getClienteIdOrNull());

        if (entity != null) {
            throw new NegocioException("Já existe um contato cadastrado com esse numero");
        }

        entity = this.contatoAssembler.toEntity(requestDTO);
        entity.setCliente(usuarioLogado.getCliente());

        entity = this.contatoService.save(entity);
        entity.validarIsFaltandoCampo();

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(entity);

        if (requestDTO.getCampoCustomizadoRequestList() != null
                && !requestDTO.getCampoCustomizadoRequestList().isEmpty()) {
            for (ContatoCampoCustomizadoRequestDTO contatoCampoCustomizadoRequestDTO : requestDTO
                    .getCampoCustomizadoRequestList()) {

                CampoCustomizado campoCustomizado = this.campoCustomizadoService.getByUuidAndCliente(
                        contatoCampoCustomizadoRequestDTO.getCampoCustomizadoUuid(),
                        usuarioLogado.getClienteIdOrNull());

                if (campoCustomizado == null) {
                    throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado!");
                }

                ContatoCampoCustomizado contatoCampoCustomizado = new ContatoCampoCustomizado();
                contatoCampoCustomizado = this.contatoCampoCustomizadoAssembler
                        .toEntityUpdate(contatoCampoCustomizadoRequestDTO, contatoCampoCustomizado);
                contatoCampoCustomizado.setCampoCustomizado(campoCustomizado);
                contatoCampoCustomizado.setContato(entity);
                contatoCampoCustomizado.setCliente(usuarioLogado.getCliente());

                contatoCampoCustomizado = this.contatoCampoCustomizadoService.save(contatoCampoCustomizado);
            }
        }
        responseDTO.setIsFaltandoCampo(entity.getIsFaltandoCampo());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ContatoResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid,
            @Valid @RequestBody ContatoRequestDTO requestDTO) {

        Contato entity = this.contatoService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        if (!entity.getNumeroWhats().equalsIgnoreCase(requestDTO.getNumeroWhats())) {
            Contato result = this.contatoService.getByNumeroWhatsAndCliente(requestDTO.getNumeroWhats(),
                    usuarioLogado.getClienteIdOrNull());

            if (result != null) {
                throw new NegocioException("Já existe um contato cadastrado com esse numero");
            }
        }

        entity = this.contatoAssembler.toEntityUpdate(requestDTO, entity);

        entity = this.contatoService.save(entity);
        entity.validarIsFaltandoCampo();

        ContatoResponseDTO responseDTO = this.contatoAssembler.toDTO(entity);

        if (requestDTO.getCampoCustomizadoRequestList() != null
                && !requestDTO.getCampoCustomizadoRequestList().isEmpty()) {

            for (ContatoCampoCustomizadoRequestDTO contatoCampoCustomizadoRequestDTO : requestDTO
                    .getCampoCustomizadoRequestList()) {

                ContatoCampoCustomizado contatoCampoCustomizado;

                if (contatoCampoCustomizadoRequestDTO.getUuid() != null) {

                    contatoCampoCustomizado = this.contatoCampoCustomizadoService
                            .getByUuidAndCliente(contatoCampoCustomizadoRequestDTO.getUuid(),
                                    usuarioLogado.getClienteIdOrNull());

                } else {
                    contatoCampoCustomizado = new ContatoCampoCustomizado();
                }

                if (contatoCampoCustomizado != null) {

                    CampoCustomizado campoCustomizado = this.campoCustomizadoService.getByUuidAndCliente(
                            contatoCampoCustomizadoRequestDTO.getCampoCustomizadoUuid(),
                            usuarioLogado.getClienteIdOrNull());

                    if (campoCustomizado == null) {
                        throw new EntidadeNaoEncontradaException("Campo Customizado não encontrado!");
                    }

                    contatoCampoCustomizado = this.contatoCampoCustomizadoAssembler
                            .toEntityUpdate(contatoCampoCustomizadoRequestDTO, contatoCampoCustomizado);
                            
                    contatoCampoCustomizado.setCampoCustomizado(campoCustomizado);
                    contatoCampoCustomizado.setContato(entity);
                    contatoCampoCustomizado.setCliente(usuarioLogado.getCliente());

                    contatoCampoCustomizado = this.contatoCampoCustomizadoService.save(contatoCampoCustomizado);

                    ContatoCampoCustomizadoResponseDTO contatoCampoCustomizadoResponseDTO = this.contatoCampoCustomizadoAssembler
                            .toDTO(contatoCampoCustomizado);

                    responseDTO.getContatosCamposCustomizados().add(contatoCampoCustomizadoResponseDTO);
                }

            }
        }



        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @PutMapping("/importar")
    public ResponseEntity<ContatoResponseDTO> update(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @Valid @RequestBody List<ContatoRequestDTO> requestsDTOs) {

        List<Contato> contatos = new ArrayList<>();

        for(ContatoRequestDTO requestDTO : requestsDTOs){
            Contato contato = this.contatoService.getByNumeroWhatsAndCliente(requestDTO.getNumeroWhats(), usuarioLogado.getClienteIdOrNull());
            if (contato != null) {
                contato = this.contatoAssembler.toEntityUpdate(requestDTO, contato);
            }else{
                contato = this.contatoAssembler.toEntity(requestDTO);
                contato.setCliente(usuarioLogado.getCliente());
            }

            contatos.add(contato);
        }

        this.contatoService.saveAll(contatos);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String uuid) {

        Contato entity = this.contatoService.getByUuidAndCliente(uuid, usuarioLogado.getClienteIdOrNull());

        if (entity == null) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }

        contatoService.deleteLogical(entity);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
