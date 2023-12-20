package com.assovio.zapja.zapjaapi.api.assemblers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.TemplateWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.TemplateWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.domain.models.TemplateWhats;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TemplateWhatsAssembler {

    private ModelMapper modelMapper;

    public TemplateWhatsResponseDTO toDTO(TemplateWhats entity) {
        return this.modelMapper.map(entity, TemplateWhatsResponseDTO.class);
    }

    public List<TemplateWhatsResponseDTO> toCollectionDTO(List<TemplateWhats> entitys) {
        return entitys.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TemplateWhats toEntity(TemplateWhatsRequestDTO requestDTO) {
        return this.modelMapper.map(requestDTO, TemplateWhats.class);
    }

    public Page<TemplateWhatsResponseDTO> toPageDTO(Page<TemplateWhats> entityPageable) {
        return entityPageable.map(this::toDTO);
    }

}
