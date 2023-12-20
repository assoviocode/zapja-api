package com.assovio.zapja.zapjaapi.api.dtos.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioWhatsRequestDTO {

    @NotBlank
    @JsonProperty("celular_origem")
    private String celularOrigem;

    @NotNull
    @JsonProperty("data_prevista")
    private Date dataPrevista;

    @NotNull
    @JsonProperty("template_whats_id")
    private Long templateWhatsId;

    @NotNull
    @JsonProperty("contatos_id")
    private List<Long> contatosId;

}
