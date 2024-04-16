package com.assovio.zapja.zapjaapi.domain.models;

import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.OffsetDateTime;
import java.util.Date;

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

        if (templateWhats != null && !templateWhats.getTexto().isEmpty() && contato != null && !contato.getNome().isEmpty()) {

            String textoTemplate = templateWhats.getTexto();
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(textoTemplate);

            while (matcher.find()) {

                String chave = matcher.group(1);
                String chaveTratada = this.getStringValida(chave);

                if (chaveTratada.equals("{{NOME}}")) {
                    textoTemplate = textoTemplate.replace(chaveTratada, contato.getNome());
                } else if (chaveTratada.equals("{{NUMERO_WHATSAPP}}")) {
                    textoTemplate = textoTemplate.replace(chaveTratada, contato.getNumeroWhats());
                } else {
                    for (ContatoCampoCustomizado contatoCampoCustomizado : this.contato.getContatosCamposCustomizados()) {

                        String rotuloChave = "{{" + this.getStringValida(contatoCampoCustomizado.getCampoCustomizado().getRotulo().replace(" ", "_").toUpperCase().trim()) + "}}";

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


    private String getStringValida(String texto) {
        String normalized = Normalizer.normalize(texto, Form.NFD);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < normalized.length(); i++) {
            char c = normalized.charAt(i);
            if (Character.UnicodeScript.of(c) != Character.UnicodeScript.UNKNOWN) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString().replace("{{{", "{{").replace("}}}", "}}").trim();
    }

    private String removeParametersByPattern(String text, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }




}
