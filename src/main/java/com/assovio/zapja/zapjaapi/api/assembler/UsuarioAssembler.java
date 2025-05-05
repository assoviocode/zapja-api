package com.assovio.zapja.zapjaapi.api.assembler;

import com.assovio.zapja.zapjaapi.api.dtos.response.LoginResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.UsuarioSimpleResponseDTO;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UsuarioAssembler {

    private ModelMapper modelMapperSTRICT;

    public LoginResponseDTO toDTO(Usuario usuario) {
        return this.modelMapperSTRICT.map(usuario, LoginResponseDTO.class);
    }

    public UsuarioSimpleResponseDTO toSimpleDTO(Usuario usuario) {
        return this.modelMapperSTRICT.map(usuario, UsuarioSimpleResponseDTO.class);
    }

    public List<UsuarioSimpleResponseDTO> toSimpleCollectionDTO(List<Usuario> users) {
        return users.stream().map(this::toSimpleDTO).collect(Collectors.toList());
    }

}
