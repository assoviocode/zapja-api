package com.assovio.zapja.zapjaapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.TemplateWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.TemplateWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.domain.model.TemplateWhats;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TemplateWhatsAssembler {

	private ModelMapper strictModelMapper;

	public TemplateWhatsResponseDTO toDTO(TemplateWhats entity) {
		return this.strictModelMapper.map(entity, TemplateWhatsResponseDTO.class);
	}

	public TemplateWhatsResponseDTO toSimpleDTO(TemplateWhats entity) {
		return this.strictModelMapper.map(entity, TemplateWhatsResponseDTO.class);
	}

	public List<TemplateWhatsResponseDTO> toCollectionDTO(List<TemplateWhats> entitys) {
		return entitys.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public TemplateWhats toEntity(TemplateWhatsRequestDTO requestDTO) {
		return this.strictModelMapper.map(requestDTO, TemplateWhats.class);
	}

	public TemplateWhats toEntityUpdate(TemplateWhatsRequestDTO requestDTO, TemplateWhats entity) {

		TypeMap<TemplateWhatsRequestDTO, TemplateWhats> typeMap = this.strictModelMapper.getTypeMap(
				TemplateWhatsRequestDTO.class,
				TemplateWhats.class);

		if (typeMap == null) {
			typeMap = this.strictModelMapper.createTypeMap(TemplateWhatsRequestDTO.class, TemplateWhats.class);

			typeMap.addMappings(mapper -> {
				mapper.skip(TemplateWhats::setId);
				mapper.skip(TemplateWhats::setUuid);
				mapper.skip(TemplateWhats::setMensagensWhats);
				mapper.skip(TemplateWhats::setCliente);
			});
		}

		this.strictModelMapper.map(requestDTO, entity);
		return entity;
	}

	public Page<TemplateWhatsResponseDTO> toPageDTO(Page<TemplateWhats> entityPageable) {
		return entityPageable.map(this::toSimpleDTO);
	}
}
