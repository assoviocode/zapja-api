package com.assovio.zapja.zapjaapi.api.assemblers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.ContatoCampoCustomizadoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ContatoCampoCustomizadoAssembler {

    private ModelMapper modelMapper;

    public ContatoCampoCustomizadoResponseDTO toDTO(ContatoCampoCustomizado entity) {
        return this.modelMapper.map(entity, ContatoCampoCustomizadoResponseDTO.class);
    }

    public List<ContatoCampoCustomizadoResponseDTO> toCollectionDTO(List<ContatoCampoCustomizado> entitys) {
        return entitys.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ContatoCampoCustomizado toEntity(ContatoCampoCustomizadoRequestDTO requestDTO,
            ContatoCampoCustomizado contatoCampoCustomizado) {
        this.modelMapper.map(requestDTO, contatoCampoCustomizado);
        return contatoCampoCustomizado;
    }

    public ContatoCampoCustomizadoResponseSimpleDTO toSimpleDTO(ContatoCampoCustomizado entity) {
        return this.modelMapper.map(entity, ContatoCampoCustomizadoResponseSimpleDTO.class);
    }

    public List<ContatoCampoCustomizadoResponseSimpleDTO> toCollectionSimpleDTO(List<ContatoCampoCustomizado> entitys) {
        return entitys.stream().map(this::toSimpleDTO).collect(Collectors.toList());
    }

    public Page<ContatoCampoCustomizadoResponseDTO> toPageDTO(Page<ContatoCampoCustomizado> entityPageable) {
        return entityPageable.map(this::toDTO);
    }

}
