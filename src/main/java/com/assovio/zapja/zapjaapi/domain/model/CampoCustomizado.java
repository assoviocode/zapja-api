package com.assovio.zapja.zapjaapi.domain.model;

import org.hibernate.annotations.SQLRestriction;

import com.assovio.zapja.zapjaapi.domain.model.contracts.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "campo_customizado")
public class CampoCustomizado extends EntityBase {

    @Column(name = "rotulo")
    private String rotulo;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "obrigatorio")
    private Boolean obrigatorio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_campo_customizado_id")
    private TipoCampoCustomizado tipoCampoCustomizado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
