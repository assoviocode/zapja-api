package com.assovio.zapja.zapjaapi.domain.models;

import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at IS NULL")
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

    public Date getDataEnvio() {
        return this.dataReal == null ? this.dataPrevista : this.dataReal;
    }

    public String getMensagemFinal() {

        String patternString = "(\\{\\{.*?\\}\\})";

        if (templateWhats != null && !templateWhats.getTexto().isEmpty() && this.contato != null && !this.contato.getNome().isEmpty()) {

            String textoTemplate = templateWhats.getTexto();
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(textoTemplate);

            while (matcher.find()) {

                String chave = matcher.group(1);
                String chaveTratada = this.getChaveTratada(chave);

                if (chaveTratada.equals("{{NOME}}")) {
                    textoTemplate = textoTemplate.replace(chaveTratada, this.contato.getNome());
                } else if (chaveTratada.equals("{{NUMERO_WHATSAPP}}")) {
                    textoTemplate = textoTemplate.replace(chaveTratada, this.contato.getNumeroWhats());
                } else {
                    for (ContatoCampoCustomizado contatoCampoCustomizado : this.contato.getContatosCamposCustomizados()) {

                        String rotuloChave = "{{" + this.getChaveTratada(contatoCampoCustomizado.getCampoCustomizado().getRotulo().replace(" ", "_").toUpperCase().trim()) + "}}";

                        if (rotuloChave.equals(chaveTratada)) {
                            textoTemplate = textoTemplate.replace(chaveTratada, contatoCampoCustomizado.getValor());
                            break;
                        }

                    }
                }
            }
            textoTemplate = removeParametersByPattern(textoTemplate, patternString);
            return !textoTemplate.isEmpty() ? textoTemplate : "";
        }

        return "";
    }

    protected String getChaveTratada(String chave) {
        String chaveTratada = chave.toUpperCase().replace(" ", "_").trim();
        chaveTratada = chaveTratada.replaceAll("[ÀÁÂÃÄÅ]", "A");
        chaveTratada = chaveTratada.replaceAll("[àáâãäå]", "a");

        chaveTratada = chaveTratada.replaceAll("[ÈÉÊË]", "E");
        chaveTratada = chaveTratada.replaceAll("[èéêë]", "e");

        chaveTratada = chaveTratada.replaceAll("[ÌÍÎÏ]", "I");
        chaveTratada = chaveTratada.replaceAll("[ìíîï]", "i");

        chaveTratada = chaveTratada.replaceAll("[ÒÓÔÖ]", "O");
        chaveTratada = chaveTratada.replaceAll("[òóôö]", "o");

        chaveTratada = chaveTratada.replaceAll("[ÙÚÛÜ]", "U");
        chaveTratada = chaveTratada.replaceAll("[ùúûü]", "u");

        return chaveTratada.replace("{{{", "{{").replace("}}}", "}}").trim();
    }


    private String removeParametersByPattern(String text, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }



}
