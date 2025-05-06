package com.assovio.zapja.zapjaapi.common;

import java.util.List;
import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assovio.zapja.zapjaapi.api.dtos.response.EnvioWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.LoginResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.MensagemWhatsResponseDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simple.EnvioWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.model.EnvioWhats;
import com.assovio.zapja.zapjaapi.domain.model.Usuario;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper strictModelMapper() {

		ModelMapper modelMapperSTRICT = new ModelMapper();
		modelMapperSTRICT.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapperSTRICT.getConfiguration().setAmbiguityIgnored(true);

		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

		modelMapperSTRICT.createTypeMap(Usuario.class, LoginResponseDTO.class)
				.<String>addMapping(src -> src.getCliente().getUuid(),
						(des, value) -> des.setClienteUuid(value));

		modelMapperSTRICT.createTypeMap(EnvioWhats.class, EnvioWhatsResponseSimpleDTO.class)
				.<String>addMapping(src -> src.getContato().getNome(),
						(des, value) -> des.setNomeContato(value))
				.<String>addMapping(src -> src.getContato().getNumeroWhats(),
						(des, value) -> des.setCelularDestino(value))
				.<String>addMapping(src -> src.getTemplateWhats().getNome(),
						(des, value) -> des.setNomeTemplateWhats(value));

		modelMapperSTRICT.createTypeMap(EnvioWhats.class, EnvioWhatsResponseDTO.class)
				.<String>addMapping(src -> src.getContato().getNumeroWhats(),
						(des, value) -> des.setCelularDestino(value))
				.<String>addMapping(src -> src.getTemplateWhats().getUuid(),
						(des, value) -> des.setTemplateWhatsUuid(value))
				.<List<MensagemWhatsResponseDTO>>addMapping(src -> src.getMensagensTratadas(),
						(des, value) -> des.setMensagensTratadas(value));

		return modelMapperSTRICT;

	}

}
