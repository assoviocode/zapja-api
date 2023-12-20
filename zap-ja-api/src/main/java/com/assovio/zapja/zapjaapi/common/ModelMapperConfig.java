package com.assovio.zapja.zapjaapi.common;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		return modelMapper;

	}

}
