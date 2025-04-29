package com.assovio.zapja.zapjaapi.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.annotations.SQLRestriction;

import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
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
@Table(name = "envio_whats")
public class EnvioWhats extends EntityBase {

    @Column(name = "celular_origem")
    private String celularOrigem;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EnumStatusEnvioWhats status;

    @Column(name = "log")
    private String log;

    @Column(name = "data_real")
    private OffsetDateTime dataReal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contato_id", nullable = false)
    private Contato contato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_whats_id", nullable = false)
    private TemplateWhats templateWhats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setup_envio_whats_id", nullable = false)
    private SetupEnvioWhats setupEnvioWhats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public List<MensagemWhats> getMensagensTratadas() {

        String patternString = "(\\{\\{.*?\\}\\})";

        List<MensagemWhats> mensagemWhatsTratadas = new ArrayList<>();

        if (this.templateWhats != null && !this.templateWhats.getMensagensWhats().isEmpty()) {

            for (MensagemWhats mensagemWhats : this.templateWhats.getMensagensWhats()) {
                String textoMensagem = mensagemWhats.getTexto();
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(textoMensagem);

                while (matcher.find()) {

                    String chave = matcher.group(1);
                    String chaveTratada = this.getChaveTratada(chave);

                    if (chaveTratada.equals("{{NOME}}")) {
                        textoMensagem = textoMensagem.replace(chaveTratada, this.contato.getNome());
                    } else if (chaveTratada.equals("{{NUMERO_WHATSAPP}}")) {
                        textoMensagem = textoMensagem.replace(chaveTratada,
                                this.contato.getNumeroWhats());
                    } else {
                        for (ContatoCampoCustomizado contatoCampoCustomizado : this.contato
                                .getContatosCamposCustomizados()) {

                            String rotuloChave = "{{" +
                                    this.getChaveTratada(contatoCampoCustomizado.getCampoCustomizado()
                                            .getRotulo().replace(" ", "_").toUpperCase().trim())
                                    + "}}";

                            if (rotuloChave.equals(chaveTratada)) {
                                textoMensagem = textoMensagem.replace(chaveTratada,
                                        contatoCampoCustomizado.getValor());
                                break;
                            }

                        }
                    }
                }
                textoMensagem = this.removeParametersByPattern(textoMensagem, patternString);

                mensagemWhats.setTexto(textoMensagem);

                mensagemWhatsTratadas.add(mensagemWhats);
            }

        }

        return mensagemWhatsTratadas;
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
