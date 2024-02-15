package com.assovio.zapja.zapjaapi.api.assemblers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.TipoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.TipoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.domain.models.TipoCampoCustomizado;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TipoCampoCustomizadoAssembler {

    private ModelMapper modelMapper;

    public TipoCampoCustomizadoResponseDTO toDTO(TipoCampoCustomizado entity) {
        return this.modelMapper.map(entity, TipoCampoCustomizadoResponseDTO.class);
    }

    public List<TipoCampoCustomizadoResponseDTO> toCollectionDTO(List<TipoCampoCustomizado> entitys) {
        return entitys.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TipoCampoCustomizado toEntity(TipoCampoCustomizadoRequestDTO requestDTO) {
        return this.modelMapper.map(requestDTO, TipoCampoCustomizado.class);
    }

    public TipoCampoCustomizado toEntity(TipoCampoCustomizadoRequestDTO requestDTO, TipoCampoCustomizado tipoCampoCustomizado) {
        this.modelMapper.map(requestDTO, tipoCampoCustomizado);
        return tipoCampoCustomizado;
    }

    public Page<TipoCampoCustomizadoResponseDTO> toPageDTO(Page<TipoCampoCustomizado> entityPageable) {
        return entityPageable.map(this::toDTO);
    }

}
