package com.assovio.zapja.zapjaapi.api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.assovio.zapja.zapjaapi.domain.service.NotificacaoService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/notificacoes")
@AllArgsConstructor
public class NotificacaoController {

    private NotificacaoService notificacaoService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam("uuidCliente") String uuidCliente) {
        return this.notificacaoService.subscribe(uuidCliente);
    }

}