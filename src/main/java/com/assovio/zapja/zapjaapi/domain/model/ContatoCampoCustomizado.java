package com.assovio.zapja.zapjaapi.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@SQLRestriction("deleted_at IS NULL")
@Table(name = "contato_campo_customizado")
public class ContatoCampoCustomizado {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private String uuid;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @ManyToOne
    @JoinColumn(name = "campo_customizado_id")
    private CampoCustomizado campoCustomizado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @PrePersist
    public void generateUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
