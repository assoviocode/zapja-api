package com.assovio.zapja.zapjaapi.domain.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.assovio.zapja.zapjaapi.domain.dao.MidiaDAO;
import com.assovio.zapja.zapjaapi.domain.exception.ErroInternoException;
import com.assovio.zapja.zapjaapi.domain.model.Midia;
import com.assovio.zapja.zapjaapi.domain.service.MidiaService;

@Service
public class MidiaServiceImpl extends GenericServiceImpl<Midia, Long, MidiaDAO>
		implements MidiaService {

	public Midia saveImage(MultipartFile file) throws IOException {
		Midia midia = new Midia();

		String nomeMidia = file.getOriginalFilename();

		if (nomeMidia != null && !nomeMidia.isBlank()) {
			if (nomeMidia.lastIndexOf('.') > 0) {
				midia.setTipo(file.getContentType());
				midia.setArquivo(file.getBytes());
			}
		}

		return this.save(midia);
	}

	public Midia buildMidiaByMultipartFile(MultipartFile file) throws IOException {
		Midia midia = new Midia();

		try {
			String nomeMidia = file.getOriginalFilename();

			if (nomeMidia != null && !nomeMidia.isBlank()) {
				if (nomeMidia.lastIndexOf('.') > 0) {
					midia.setTipo(file.getContentType());
					midia.setArquivo(file.getBytes());
				}
			}

		} catch (IOException ex) {
			throw new ErroInternoException(
					"Erro Interno: Não foi possível converter o arquivo enviado para uma mídia válida");
		}

		return midia;
	}

}
