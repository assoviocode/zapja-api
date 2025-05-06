package com.assovio.zapja.zapjaapi.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.assovio.zapja.zapjaapi.domain.model.Midia;

import jakarta.annotation.Nonnull;
import lombok.NonNull;

@Service
public class NotificacaoService {

	private final List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());
	private final Map<String, SseEmitter> sseEmittersPorCliente = new ConcurrentHashMap<>();

	public SseEmitter subscribe(String uuidCliente) {
		SseEmitter sseEmitter = new SseEmitter(3600000L); // 1 hora

		sseEmittersPorCliente.put(uuidCliente, sseEmitter);

		synchronized (sseEmitters) {
			sseEmitters.add(sseEmitter);
		}

		sseEmitter.onCompletion(() -> {
			synchronized (sseEmitters) {
				sseEmitters.remove(sseEmitter);
			}
			sseEmittersPorCliente.remove(uuidCliente);
		});

		sseEmitter.onTimeout(() -> {
			sseEmitter.complete();
			synchronized (sseEmitters) {
				sseEmitters.remove(sseEmitter);
			}
			sseEmittersPorCliente.remove(uuidCliente);
		});

		return sseEmitter;
	}

	public void sendNotificationToAll(@NonNull String message) {
		synchronized (sseEmitters) {
			for (SseEmitter emitter : sseEmitters) {
				try {
					emitter.send(SseEmitter.event().data(message));
				} catch (Exception e) {
					emitter.complete();
				}
			}
		}
	}

	public void sendAutenticacaoWhatsToAll(@Nonnull Midia midia) {
		synchronized (sseEmitters) {
			for (SseEmitter emitter : sseEmitters) {
				try {
					emitter.send(SseEmitter.event()
							.name("autenticacao_whats")
							.data(midia.getBase())
							.id(UUID.randomUUID().toString())
							.reconnectTime(1000));
				} catch (Exception e) {
					emitter.complete();
				}
			}
		}
	}

	public void sendAutenticacaoWhatsTo(@Nonnull String uuidCliente, @Nonnull Midia midia) {
		
		SseEmitter emitter = sseEmittersPorCliente.get(uuidCliente);

		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event()
						.name("autenticacao_whats")
						.data(midia.getBase())
						.id(UUID.randomUUID().toString())
						.reconnectTime(1000));
			} catch (Exception e) {
				emitter.complete();
				sseEmittersPorCliente.remove(uuidCliente);
			}
		}
	}
}
