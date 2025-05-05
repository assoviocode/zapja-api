package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.EnvioWhatsDAO;
import com.assovio.zapja.zapjaapi.domain.model.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.model.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.service.CampoCustomizadoService;
import com.assovio.zapja.zapjaapi.domain.service.EnvioWhatsService;

@Service
public class EnvioWhatsServiceImpl
                extends GenericServiceImpl<EnvioWhats, Long, EnvioWhatsDAO>
                implements EnvioWhatsService {

        @Autowired
        private CampoCustomizadoService campoCustomizadoService;

        @Override
        public Page<EnvioWhats> getByFilters(String nomeContato, String numeroWhatsapp, EnumStatusEnvioWhats status,
                        String celularOrigem,
                        String templateWhatsUuid,
                        String contatoUuid,
                        Long clienteId,
                        Pageable pageable) {
                return this.dao.findByFilters(
                                nomeContato,
                                numeroWhatsapp,
                                status,
                                celularOrigem,
                                templateWhatsUuid,
                                contatoUuid,
                                clienteId,
                                pageable);

        }

        @Override
        public EnvioWhats getByUuidAndCliente(String uuid, Long clienteId) {
                return this.dao.findFirstByUuidAndClienteId(uuid, clienteId);
        }

        @Override
        public EnvioWhats getProximo(String celularOrigem, Long clienteId) {

                Boolean envioInvalido = true;
                EnvioWhats envioWhats = null;

                while (envioInvalido) {

                        envioWhats = this.dao.findFirstByCelularOrigemAndStatusAndClienteId(celularOrigem,
                                        EnumStatusEnvioWhats.NA_FILA, clienteId);

                        if (envioWhats != null) {

                                if (this.isEnvioWhatsValido(envioWhats)) {
                                        envioWhats.setStatus(EnumStatusEnvioWhats.EM_ANDAMENTO);
                                        this.save(envioWhats);
                                        envioInvalido = false;
                                } else {
                                        envioWhats.setStatus(EnumStatusEnvioWhats.ERRO);
                                        envioWhats.setLog("Envio inválido: Campo obrigatório não preenchido");
                                        this.save(envioWhats);
                                }
                        } else {
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
                for (ContatoCampoCustomizado contatoCampoCustomizado : envioWhats.getContato()
                                .getContatosCamposCustomizados()) {
                        ids.add(contatoCampoCustomizado.getCampoCustomizado().getId());
                }

                if (!ids.containsAll(idsObrigatorios)) {
                        return false;
                }

                return true;

        }
}
