package com.assovio.zapja.zapjaapi.api.assemblers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.zapja.zapjaapi.api.dtos.request.ClienteRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.ClienteResponseDTO;
import com.assovio.zapja.zapjaapi.domain.models.Cliente;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClienteAssembler {

    private ModelMapper modelMapper;

    public ClienteResponseDTO toDTO(Cliente entity) {
        return this.modelMapper.map(entity, ClienteResponseDTO.class);
    }

    public List<ClienteResponseDTO> toCollectionDTO(List<Cliente> entitys) {
        return entitys.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Cliente toEntity(ClienteRequestDTO requestDTO) {
        return this.modelMapper.map(requestDTO, Cliente.class);
    }

    public Cliente toEntity(ClienteRequestDTO requestDTO, Cliente contato) {
        this.modelMapper.map(requestDTO, contato);
        return contato;
    }

    public Page<ClienteResponseDTO> toPageDTO(Page<Cliente> entityPageable) {
        return entityPageable.map(this::toDTO);
    }

}
