package com.assovio.zapja.zapjaapi.domain.models;

import java.time.OffsetDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "envio_whats")
public class EnvioWhats {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "celular_origem")
    private String celularOrigem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(50) DEFAULT 'NOVO'")
    private EnumStatusEnvioWhats status;

    @Column(name = "log")
    private String log;

    @Column(name = "servidor")
    private String servidor;

    @Column(name = "data_prevista")
    private Date dataPrevista;

    @Column(name = "data_real")
    private Date dataReal;

    @ManyToOne
    @JoinColumn(name = "template_whats_id")
    private TemplateWhats templateWhats;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

}
