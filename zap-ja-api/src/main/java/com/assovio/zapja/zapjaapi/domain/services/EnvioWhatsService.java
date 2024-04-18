package com.assovio.zapja.zapjaapi.domain.services;

import com.assovio.zapja.zapjaapi.domain.daos.EnvioWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.models.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class EnvioWhatsService {

    @Autowired
    private EnvioWhatsDAO dao;

    @Autowired
    private CampoCustomizadoService campoCustomizadoService;

    public List<EnvioWhats> getAll() {
        return (List<EnvioWhats>) this.dao.findAll();
    }

    public EnvioWhats getById(Long id) {
        return this.dao.findById(id).orElse(null);
    }

    public EnvioWhats save(EnvioWhats entity) {
        return this.dao.save(entity);
    }

    public void delete(EnvioWhats entity) {
        this.dao.delete(entity);
    }

    public void deleteLogical(EnvioWhats entity) {
        entity.setDeletedAt(OffsetDateTime.now());
        this.save(entity);
    }

    public Page<EnvioWhats> getByFilters(String nomeContato, String numeroWhatsapp, EnumStatusEnvioWhats status,
            String celularOrigem, Long templateWhatsId,
            Long contatoId,
            Date dataPrevista,
            Pageable pageable) {
        return this.dao.findByFilters(nomeContato, numeroWhatsapp, status, celularOrigem, templateWhatsId, contatoId, dataPrevista,
                pageable);
    }

    public EnvioWhats getProximo() {

        Boolean envioInvalido = true;
        EnvioWhats envioWhats = null;

        while(envioInvalido){

            List<EnvioWhats> enviosWhats = this.dao.findFirstByStatus(EnumStatusEnvioWhats.NA_FILA);

            if (enviosWhats != null && !enviosWhats.isEmpty()) {

                envioWhats = enviosWhats.get(0);

                if(this.isEnvioWhatsValido(envioWhats)){
                    envioWhats.setStatus(EnumStatusEnvioWhats.EM_ANDAMENTO);
                    this.save(envioWhats);
                    envioInvalido = false;
                }else{
                    envioWhats.setStatus(EnumStatusEnvioWhats.ERRO);
                    envioWhats.setLog("Envio inválido: Campo obrigatório não preenchido");
                    this.save(envioWhats);
                }
            }else{
                envioInvalido = false;
            }

        }


        return envioWhats;
    }


    private Boolean isEnvioWhatsValido(EnvioWhats envioWhats) {

        List<CampoCustomizado> camposCustomizadosObrigatorios = this.campoCustomizadoService.getObrigatorios();
        if (camposCustomizadosObrigatorios == null || camposCustomizadosObrigatorios.isEmpty()) {
            return true;
        }

        List<Long> idsObrigatorios = new LinkedList<>();
        for (CampoCustomizado campoCustomizadoObrigatorio : camposCustomizadosObrigatorios) {
            idsObrigatorios.add(campoCustomizadoObrigatorio.getId());
        }

        List<Long> ids = new LinkedList<>();
        for (ContatoCampoCustomizado contatoCampoCustomizado : envioWhats.getContato().getContatosCamposCustomizados()) {
            ids.add(contatoCampoCustomizado.getCampoCustomizado().getId());
        }

        if(!ids.containsAll(idsObrigatorios)){
            return false;
        }

        return true;

    }
}
