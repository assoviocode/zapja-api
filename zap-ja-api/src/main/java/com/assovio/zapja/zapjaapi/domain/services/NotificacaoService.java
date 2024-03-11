package com.assovio.zapja.zapjaapi.domain.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
