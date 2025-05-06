package com.assovio.zapja.zapjaapi.api.dtos.response;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusRoboCliente;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoboClienteResponseDTO {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("celular_origem")
    private String celularOrigem;

    @JsonProperty("status")
    private EnumStatusRoboCliente status;

}
