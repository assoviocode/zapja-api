package com.assovio.zapja.zapjaapi.api.assemblers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.ContatoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.models.Contato;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ContatoAssembler {

    private ModelMapper modelMapper;

    public ContatoResponseDTO toDTO(Contato entity) {
        return this.modelMapper.map(entity, ContatoResponseDTO.class);
    }

    public List<ContatoResponseDTO> toCollectionDTO(List<Contato> entitys) {
        return entitys.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Contato toEntity(ContatoRequestDTO requestDTO) {
        return this.modelMapper.map(requestDTO, Contato.class);
    }

    public ContatoResponseSimpleDTO toSimpleDTO(Contato entity) {
        return this.modelMapper.map(entity, ContatoResponseSimpleDTO.class);
    }

    public Page<ContatoResponseSimpleDTO> toPageDTO(Page<Contato> entityPageable) {
        return entityPageable.map(this::toSimpleDTO);
    }

}
