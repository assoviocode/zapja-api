package com.assovio.zapja.zapjaapi.common;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper strictModelMapper() {

		ModelMapper modelMapperSTRICT = new ModelMapper();
		modelMapperSTRICT.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapperSTRICT.getConfiguration().setAmbiguityIgnored(true);

		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

		return modelMapperSTRICT;

	}

}
