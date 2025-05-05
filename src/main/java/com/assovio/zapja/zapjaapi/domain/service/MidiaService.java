package com.assovio.zapja.zapjaapi.domain.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.assovio.zapja.zapjaapi.domain.model.Midia;

public interface MidiaService extends GenericService<Midia, Long> {

    public Midia saveImage(MultipartFile file) throws IOException;

    public Midia buildMidiaByMultipartFile(MultipartFile file) throws IOException;

}
