package com.assovio.zapja.zapjaapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.MensagemWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.MensagemWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.MensagemWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.model.MensagemWhats;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class MensagemWhatsAssembler {

	private ModelMapper strictModelMapper;

	public MensagemWhatsResponseDTO toDTO(MensagemWhats entity) {
		return this.strictModelMapper.map(entity, MensagemWhatsResponseDTO.class);
	}

	public MensagemWhatsResponseSimpleDTO toSimpleDTO(MensagemWhats entity) {
		return this.strictModelMapper.map(entity, MensagemWhatsResponseSimpleDTO.class);
	}

	public List<MensagemWhatsResponseDTO> toCollectionDTO(List<MensagemWhats> entitys) {
		return entitys.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public MensagemWhats toEntity(MensagemWhatsRequestDTO requestDTO) {
		return this.strictModelMapper.map(requestDTO, MensagemWhats.class);
	}

	public MensagemWhats toEntityUpdate(MensagemWhatsRequestDTO requestDTO, MensagemWhats entity) {

		TypeMap<MensagemWhatsRequestDTO, MensagemWhats> typeMap = this.strictModelMapper.getTypeMap(
				MensagemWhatsRequestDTO.class,
				MensagemWhats.class);

		if (typeMap == null) {
			typeMap = this.strictModelMapper.createTypeMap(MensagemWhatsRequestDTO.class, MensagemWhats.class);

			typeMap.addMappings(mapper -> {
				mapper.skip(MensagemWhats::setId);
				mapper.skip(MensagemWhats::setUuid);
				mapper.skip(MensagemWhats::setTemplateWhats);
				mapper.skip(MensagemWhats::setCliente);
			});
		}

		this.strictModelMapper.map(requestDTO, entity);
		return entity;
	}

	public Page<MensagemWhatsResponseSimpleDTO> toPageDTO(Page<MensagemWhats> entityPageable) {
		return entityPageable.map(this::toSimpleDTO);
	}
}
