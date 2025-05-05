package com.assovio.zapja.zapjaapi.domain.model;

import java.time.OffsetDateTime;
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
@Table(name = "setup_envio_whats")
public class SetupEnvioWhats extends EntityBase {

    @Column(name = "data_prevista_inicio", nullable = false)
    private OffsetDateTime dataPrevistaInicio;

    @Column(name = "intervalo_entre_mensagem_min", nullable = true)
    private Integer intervaloEntreMensagemMin;

    @Column(name = "intervalo_entre_mensagem_max", nullable = false)
    private Integer intervaloEntreMensagemMax;

    @Column(name = "is_recorrente", nullable = true)
    private Boolean isRecorrente;

    @Column(name = "dias_recorrencia", nullable = true)
    private Integer diasRecorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "setupEnvioWhats", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnvioWhats> enviosWhats = new ArrayList<>();

    public void setIntervaloEntreMensagemMin(Integer intervaloEntreMensagemMin) {
        if (intervaloEntreMensagemMin != null) {
            this.intervaloEntreMensagemMin = intervaloEntreMensagemMin;
        } else {
            this.intervaloEntreMensagemMin = 15;
        }
    }

}
