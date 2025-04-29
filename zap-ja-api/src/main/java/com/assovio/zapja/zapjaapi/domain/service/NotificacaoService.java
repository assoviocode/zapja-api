package com.assovio.zapja.zapjaapi.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.NonNull;

@Service
public class NotificacaoService {

	private final List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

	public SseEmitter subscribe() {

		SseEmitter sseEmitter = new SseEmitter(3600000L);
		synchronized (this.sseEmitters) {
			sseEmitter.onCompletion(() -> {
				synchronized (this.sseEmitters) {
					this.sseEmitters.remove(sseEmitter);
				}
			});
			sseEmitter.onTimeout(sseEmitter::complete);
			this.sseEmitters.add(sseEmitter);
		}
		return sseEmitter;
	}

	public void sendNotificationToAll(@NonNull String message) {

		for (SseEmitter emmiter : sseEmitters) {
			try {
				emmiter.send(SseEmitter.event().data(message));
			} catch (Exception e) {
				emmiter.complete();
			}
		}
	}

	public void sendQrCodeToAll(@NonNull byte[] imageBytes) {
		for (SseEmitter emitter : sseEmitters) {
			try {
				emitter.send(SseEmitter.event()
						.name("qr_code")
						.data(imageBytes)
						.id(UUID.randomUUID().toString())
						.reconnectTime(1000));
			} catch (Exception e) {
				emitter.complete();
			}
		}
	}

}
