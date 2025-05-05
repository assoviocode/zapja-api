package com.assovio.zapja.zapjaapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.EnvioWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.EnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.EnvioWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EnvioWhatsAssembler {

	private ModelMapper modelMapperSTRICT;

	public EnvioWhatsResponseDTO toDTO(EnvioWhats entity) {
		return this.modelMapperSTRICT.map(entity, EnvioWhatsResponseDTO.class);
	}

	public List<EnvioWhatsResponseDTO> toCollectionDTO(List<EnvioWhats> entitys) {
		return entitys.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public EnvioWhats toEntity(EnvioWhatsRequestDTO requestDTO) {
		return this.modelMapperSTRICT.map(requestDTO, EnvioWhats.class);
	}

	public EnvioWhatsResponseSimpleDTO toSimpleDTO(EnvioWhats entity) {
		return this.modelMapperSTRICT.map(entity, EnvioWhatsResponseSimpleDTO.class);
	}

	public List<EnvioWhatsResponseSimpleDTO> toCollectionSimpleDTO(List<EnvioWhats> entitys) {
		return entitys.stream().map(this::toSimpleDTO).collect(Collectors.toList());
	}

	public Page<EnvioWhatsResponseSimpleDTO> toPageDTO(Page<EnvioWhats> entityPageable) {
		return entityPageable.map(this::toSimpleDTO);
	}

}
