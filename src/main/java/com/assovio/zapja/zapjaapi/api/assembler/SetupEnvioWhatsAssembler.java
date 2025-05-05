package com.assovio.zapja.zapjaapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.SetupEnvioWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.SetupEnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.domain.model.SetupEnvioWhats;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SetupEnvioWhatsAssembler {

	private ModelMapper strictModelMapper;

	public SetupEnvioWhatsResponseDTO toDTO(SetupEnvioWhats entity) {
		return this.strictModelMapper.map(entity, SetupEnvioWhatsResponseDTO.class);
	}

	public SetupEnvioWhatsResponseDTO toSimpleDTO(SetupEnvioWhats entity) {
		return this.strictModelMapper.map(entity, SetupEnvioWhatsResponseDTO.class);
	}

	public List<SetupEnvioWhatsResponseDTO> toCollectionDTO(List<SetupEnvioWhats> entitys) {
		return entitys.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public SetupEnvioWhats toEntity(SetupEnvioWhatsRequestDTO requestDTO) {
		return this.strictModelMapper.map(requestDTO, SetupEnvioWhats.class);
	}

	public SetupEnvioWhats toEntityUpdate(SetupEnvioWhatsRequestDTO requestDTO, SetupEnvioWhats entity) {

		TypeMap<SetupEnvioWhatsRequestDTO, SetupEnvioWhats> typeMap = this.strictModelMapper.getTypeMap(
				SetupEnvioWhatsRequestDTO.class,
				SetupEnvioWhats.class);

		if (typeMap == null) {
			typeMap = this.strictModelMapper.createTypeMap(SetupEnvioWhatsRequestDTO.class, SetupEnvioWhats.class);

			typeMap.addMappings(mapper -> {
				mapper.skip(SetupEnvioWhats::setId);
				mapper.skip(SetupEnvioWhats::setUuid);
				mapper.skip(SetupEnvioWhats::setCliente);
			});
		}

		this.strictModelMapper.map(requestDTO, entity);
		return entity;
	}

	public Page<SetupEnvioWhatsResponseDTO> toPageDTO(Page<SetupEnvioWhats> entityPageable) {
		return entityPageable.map(this::toSimpleDTO);
	}
}
