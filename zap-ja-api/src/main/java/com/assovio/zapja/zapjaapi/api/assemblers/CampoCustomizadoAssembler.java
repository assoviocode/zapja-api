package com.assovio.zapja.zapjaapi.api.assemblers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.CampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.CampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.domain.models.CampoCustomizado;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CampoCustomizadoAssembler {

    private ModelMapper modelMapper;

    public CampoCustomizadoResponseDTO toDTO(CampoCustomizado entity) {
        return this.modelMapper.map(entity, CampoCustomizadoResponseDTO.class);
    }

    public List<CampoCustomizadoResponseDTO> toCollectionDTO(List<CampoCustomizado> entitys) {
        return entitys.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CampoCustomizado toEntity(CampoCustomizadoRequestDTO requestDTO) {
        return this.modelMapper.map(requestDTO, CampoCustomizado.class);
    }

    public CampoCustomizado toEntity(CampoCustomizadoRequestDTO requestDTO, CampoCustomizado campoCustomizado) {
        this.modelMapper.map(requestDTO, campoCustomizado);
        return campoCustomizado;
    }

    public Page<CampoCustomizadoResponseDTO> toPageDTO(Page<CampoCustomizado> entityPageable) {
        return entityPageable.map(this::toDTO);
    }

}
