package com.assovio.zapja.zapjaapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.ContatoCampoCustomizadoRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ContatoCampoCustomizadoResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.ContatoCampoCustomizadoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.model.ContatoCampoCustomizado;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ContatoCampoCustomizadoAssembler {

	private ModelMapper strictModelMapper;

	public ContatoCampoCustomizadoResponseDTO toDTO(ContatoCampoCustomizado entity) {
		return this.strictModelMapper.map(entity, ContatoCampoCustomizadoResponseDTO.class);
	}

	public ContatoCampoCustomizadoResponseSimpleDTO toSimpleDTO(ContatoCampoCustomizado entity) {
		return this.strictModelMapper.map(entity, ContatoCampoCustomizadoResponseSimpleDTO.class);
	}

	public List<ContatoCampoCustomizadoResponseDTO> toCollectionDTO(List<ContatoCampoCustomizado> entitys) {
		return entitys.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<ContatoCampoCustomizadoResponseSimpleDTO> toCollectionSimpleDTO(List<ContatoCampoCustomizado> entitys) {
		return entitys.stream().map(this::toSimpleDTO).collect(Collectors.toList());
	}

	public ContatoCampoCustomizado toEntity(ContatoCampoCustomizadoRequestDTO requestDTO) {
		return this.strictModelMapper.map(requestDTO, ContatoCampoCustomizado.class);
	}

	public ContatoCampoCustomizado toEntityUpdate(ContatoCampoCustomizadoRequestDTO requestDTO,
			ContatoCampoCustomizado entity) {

		TypeMap<ContatoCampoCustomizadoRequestDTO, ContatoCampoCustomizado> typeMap = this.strictModelMapper.getTypeMap(
				ContatoCampoCustomizadoRequestDTO.class,
				ContatoCampoCustomizado.class);

		if (typeMap == null) {
			typeMap = this.strictModelMapper.createTypeMap(ContatoCampoCustomizadoRequestDTO.class,
					ContatoCampoCustomizado.class);

			typeMap.addMappings(mapper -> {
				mapper.skip(ContatoCampoCustomizado::setId);
				mapper.skip(ContatoCampoCustomizado::setUuid);
				mapper.skip(ContatoCampoCustomizado::setCliente);
			});
		}

		this.strictModelMapper.map(requestDTO, entity);
		return entity;
	}

	public Page<ContatoCampoCustomizadoResponseSimpleDTO> toPageDTO(Page<ContatoCampoCustomizado> entityPageable) {
		return entityPageable.map(this::toSimpleDTO);
	}
}
