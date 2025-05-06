package com.assovio.zapja.zapjaapi.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.assovio.zapja.zapjaapi.domain.model.contracts.EntityBase;

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
@Table(name = "contato")
public class Contato extends EntityBase {

    @Column(name = "numero_whats")
    private String numeroWhats;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "contato")
    private List<ContatoCampoCustomizado> contatosCamposCustomizados;

    public Boolean getIsFaltandoCampo(){
        for (ContatoCampoCustomizado campoCustomizado : this.contatosCamposCustomizados){
            if(
                    ((campoCustomizado != null) && campoCustomizado.getCampoCustomizado().getObrigatorio())
                            &&
                    ((campoCustomizado.getValor() == null) || campoCustomizado.getValor().isEmpty())
            ){
                return true;
            }
        }

        return false;
    }

    public void setDeletedAt(OffsetDateTime offsetDateTime) {

        for (ContatoCampoCustomizado contatoCampoCustomizado : this.contatosCamposCustomizados) {
            contatoCampoCustomizado.setDeletedAt(offsetDateTime);
        }

        this.deletedAt = offsetDateTime;
    }

}
