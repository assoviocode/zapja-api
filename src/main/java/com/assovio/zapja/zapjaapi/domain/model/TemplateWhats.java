package com.assovio.zapja.zapjaapi.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.assovio.zapja.zapjaapi.domain.model.contracts.EntityBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SQLRestriction("deleted_at IS NULL")
@Table(name = "template_whats")
public class TemplateWhats extends EntityBase {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "chave", nullable = true)
    private String chave;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "templateWhats", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MensagemWhats> mensagensWhats = new ArrayList<>();


    public void setNome(String nome) {
        this.nome = nome;
        this.setChave(this.nome);
    }

    public void setChave(String chave) {

        if (chave != null && !chave.isBlank())
            this.chave = chave.replace(" ", "_").toUpperCase().trim();
        else {
            this.chave = null;
        }
    }

    public void setAtivo(Boolean ativo) {

        if (ativo != null) {
            this.ativo = ativo;
        } else {
            this.ativo = ativo;
        }
    }

}
