package com.assovio.zapja.zapjaapi.api.dtos.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemWhatsRequestDTO {

    @NotNull
    private Integer ordemEnvio;

    private String texto;

    private MultipartFile midia;
}
