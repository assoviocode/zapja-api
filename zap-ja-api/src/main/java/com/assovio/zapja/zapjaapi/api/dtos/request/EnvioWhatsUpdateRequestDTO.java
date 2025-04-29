package com.assovio.zapja.zapjaapi.api.dtos.request;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioWhatsUpdateRequestDTO {

    @NotNull
    @JsonProperty("status")
    private EnumStatusEnvioWhats status;

    @JsonProperty("log")
    private String log;

}
