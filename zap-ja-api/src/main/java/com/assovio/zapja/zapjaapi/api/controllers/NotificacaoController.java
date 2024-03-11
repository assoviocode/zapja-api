package com.assovio.zapja.zapjaapi.api.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.assovio.zapja.zapjaapi.domain.services.NotificacaoService;

import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/notificacoes")
@AllArgsConstructor
public class NotificacaoController {

    private NotificacaoService notificacaoService;

    @GetMapping(value = "/subscribe")
    public SseEmitter subscribe() throws IOException {
        return this.notificacaoService.subscribe();
    }

}