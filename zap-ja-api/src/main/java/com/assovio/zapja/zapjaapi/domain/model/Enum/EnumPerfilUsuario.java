package com.assovio.zapja.zapjaapi.domain.model.Enum;

public enum EnumPerfilUsuario {
    MASTER("master"),
    ROBO("robo"),
    ADMINISTRADOR("administrador"),
    OPERACIONAL("operacional");

    private String perfil;

    EnumPerfilUsuario(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }

}
