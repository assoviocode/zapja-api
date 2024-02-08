package com.assovio.zapja.zapjaapi.domain.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.daos.EnvioWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;

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

    public Page<EnvioWhats> getByFilters(String nomeContato, String numeroWhatsapp, EnumStatusEnvioWhats status, String celularOrigem, Long templateWhatsId,
            Long contatoId,
            Pageable pageable) {
        return this.dao.findByFilters(nomeContato, numeroWhatsapp, status, celularOrigem, templateWhatsId, contatoId, pageable);
    }

    public EnvioWhats getProximo() {
        EnvioWhats envioWhats = this.dao.findFirstByStatus(EnumStatusEnvioWhats.NA_FILA);

        if (envioWhats != null) {
            envioWhats.setStatus(EnumStatusEnvioWhats.EM_ANDAMENTO);
            this.save(envioWhats);

            return envioWhats;
        }

        return null;
    }
}
