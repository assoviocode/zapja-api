package com.assovio.zapja.zapjaapi.domain.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.ClienteDAO;
import com.assovio.zapja.zapjaapi.domain.model.Cliente;
import com.assovio.zapja.zapjaapi.domain.service.ClienteService;

@Service
public class ClienteServiceImpl extends GenericServiceImpl<Cliente, Long, ClienteDAO>
                implements ClienteService {

        @Override
        public Page<Cliente> getByFilters(String nome, Pageable pageable) {
                return this.dao.findByFilters(nome, pageable);
        }

        @Override
        public Cliente getByUuid(String uuid) {
                return this.dao.findFirstByUuid(uuid);
        }

}
