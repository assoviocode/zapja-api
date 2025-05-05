package com.assovio.zapja.zapjaapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.ContatoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.model.Contato;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ContatoAssembler {

    private ModelMapper strictModelMapper;

    public ContatoResponseDTO toDTO(Contato entity) {
        return this.strictModelMapper.map(entity, ContatoResponseDTO.class);
    }

    public ContatoResponseSimpleDTO toSimpleDTO(Contato entity) {
        return this.strictModelMapper.map(entity, ContatoResponseSimpleDTO.class);
    }

    public List<ContatoResponseDTO> toCollectionDTO(List<Contato> entitys) {
        return entitys.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ContatoResponseSimpleDTO> toCollectionSimpleDTO(List<Contato> entitys) {
        return entitys.stream().map(this::toSimpleDTO).collect(Collectors.toList());
    }

    public Contato toEntity(ContatoRequestDTO requestDTO) {
        return this.strictModelMapper.map(requestDTO, Contato.class);
    }

    public Contato toEntityUpdate(ContatoRequestDTO requestDTO, Contato entity) {

        TypeMap<ContatoRequestDTO, Contato> typeMap = this.strictModelMapper.getTypeMap(
                ContatoRequestDTO.class,
                Contato.class);

        if (typeMap == null) {
            typeMap = this.strictModelMapper.createTypeMap(ContatoRequestDTO.class, Contato.class);

            typeMap.addMappings(mapper -> {
                mapper.skip(Contato::setId);
                mapper.skip(Contato::setUuid);
                mapper.skip(Contato::setCliente);
            });
        }

        this.strictModelMapper.map(requestDTO, entity);
        return entity;
    }

    public Page<ContatoResponseSimpleDTO> toPageDTO(Page<Contato> entityPageable) {
        return entityPageable.map(this::toSimpleDTO);
    }
}
