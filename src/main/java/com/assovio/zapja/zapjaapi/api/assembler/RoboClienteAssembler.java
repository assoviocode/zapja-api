package com.assovio.zapja.zapjaapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.response.RoboClienteResponseDTO;
import com.assovio.zapja.zapjaapi.domain.model.RoboCliente;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RoboClienteAssembler {

	private ModelMapper modelMapperSTRICT;

	public RoboClienteResponseDTO toDTO(RoboCliente entity) {
		return this.modelMapperSTRICT.map(entity, RoboClienteResponseDTO.class);
	}

	public List<RoboClienteResponseDTO> toCollectionDTO(List<RoboCliente> entitys) {
		return entitys.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
