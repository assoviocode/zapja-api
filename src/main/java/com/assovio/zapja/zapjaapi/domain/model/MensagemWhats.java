package com.assovio.zapja.zapjaapi.domain.model;

import org.hibernate.annotations.SQLRestriction;

import com.assovio.zapja.zapjaapi.domain.model.contracts.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "mensagem_whats")
public class MensagemWhats extends EntityBase {

    @Column(name = "ordem_envio", nullable = false)
    private Integer ordemEnvio;

    @Column(name = "texto", nullable = true)
    private String texto;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "midia_id", referencedColumnName = "id", nullable = true)
    private Midia midia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_whats_id", nullable = false)
    private TemplateWhats templateWhats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
