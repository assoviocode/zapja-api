package com.assovio.zapja.zapjaapi.domain.service.impl;

import org.springframework.stereotype.Service;

import com.assovio.zapja.zapjaapi.domain.dao.RoboClienteDAO;
import com.assovio.zapja.zapjaapi.domain.model.RoboCliente;
import com.assovio.zapja.zapjaapi.domain.service.RoboClienteService;

@Service
public class RoboClienteServiceImpl extends GenericServiceImpl<RoboCliente, Long, RoboClienteDAO>
        implements RoboClienteService {

}
