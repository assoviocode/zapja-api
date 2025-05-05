package com.assovio.zapja.zapjaapi.domain.model;

import org.hibernate.annotations.SQLRestriction;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusRoboCliente;
import com.assovio.zapja.zapjaapi.domain.model.contracts.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "robo_cliente")
public class RoboCliente extends EntityBase {

    @Column(name = "celular_origem")
    private String celularOrigem;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumStatusRoboCliente status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
