package com.assovio.zapja.zapjaapi.domain.services;

import com.assovio.zapja.zapjaapi.domain.daos.EnvioWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Service
public class EnvioWhatsService {

    @Autowired
    private EnvioWhatsDAO dao;

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
        List<EnvioWhats> enviosWhats = this.dao.findFirstValidByStatus(EnumStatusEnvioWhats.NA_FILA.name());

        if (enviosWhats != null && !enviosWhats.isEmpty()) {

            EnvioWhats envioWhats = enviosWhats.get(0);
            envioWhats.setStatus(EnumStatusEnvioWhats.EM_ANDAMENTO);
            this.save(envioWhats);

            return envioWhats;

        }

        return null;
    }
}
