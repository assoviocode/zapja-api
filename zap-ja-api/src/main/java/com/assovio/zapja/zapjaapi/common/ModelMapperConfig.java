package com.assovio.zapja.zapjaapi.common;

import com.assovio.zapja.zapjaapi.api.dtos.request.EnvioWhatsRequestDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.*;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.ContatoCampoCustomizadoResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.api.dtos.response.simples.EnvioWhatsResponseSimpleDTO;
import com.assovio.zapja.zapjaapi.domain.models.CampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.models.Contato;
import com.assovio.zapja.zapjaapi.domain.models.ContatoCampoCustomizado;
import com.assovio.zapja.zapjaapi.domain.models.Enum.EnumStatusEnvioWhats;
import com.assovio.zapja.zapjaapi.domain.models.EnvioWhats;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		// // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		// // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// // modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		modelMapper.createTypeMap(EnvioWhatsRequestDTO.class, EnvioWhats.class)
				.<Long>addMapping(src -> null, (des, value) -> des.setId(value));

		modelMapper.createTypeMap(EnvioWhats.class, EnvioWhatsResponseDTO.class)
				.<TemplateWhatsResponseDTO>addMapping(src -> src.getTemplateWhats(),
						(des, value) -> des.setTemplateWhatsResponseDTO(value))
				.<ContatoResponseDTO>addMapping(src -> src.getContato(),
						(des, value) -> des.setContatoResponseDTO(value))
				.<String>addMapping(src -> src.getMensagemFinal(),
						(des, value) -> des.setMensagemFinal(value))
		;

		modelMapper.createTypeMap(EnvioWhats.class, EnvioWhatsResponseSimpleDTO.class)
				.<String>addMapping(src -> src.getContato().getNumeroWhats(), (des, value) -> des.setNumeroWhats(value))
				.<String>addMapping(src -> src.getContato().getNome(), (des, value) -> des.setNomeContato(value))
				.<String>addMapping(src -> src.getTemplateWhats().getNome(), (des, value) -> des.setNomeTemplate(value))
				.<String>addMapping(src -> src.getCelularOrigem(), (des, value) -> des.setNumeroOrigem(value))
				.<EnumStatusEnvioWhats>addMapping(src -> src.getStatus(), (des, value) -> des.setStatus(value))
				.<Date>addMapping(src -> src.getDataEnvio(), (des, value) -> des.setDataEnvio(value));

		modelMapper.createTypeMap(ContatoCampoCustomizado.class, ContatoCampoCustomizadoResponseSimpleDTO.class)
				.<Long>addMapping(src -> src.getCampoCustomizado().getId(),
						(des, value) -> des.setCampoCustomizadoId(value))
				.<String>addMapping(src -> src.getCampoCustomizado().getRotulo(), (des, value) -> des.setRotulo(value))
				.<String>addMapping(src -> src.getCampoCustomizado().getTipoCampoCustomizado().getMascara(),
						(des, value) -> des.setMascara(value));

		modelMapper.createTypeMap(ContatoCampoCustomizado.class, ContatoCampoCustomizadoResponseDTO.class)
				.<CampoCustomizadoResponseDTO>addMapping(src -> src.getCampoCustomizado(),
						(des, value) -> des.setCampoCustomizadoResponseDTO(value));

		modelMapper.createTypeMap(CampoCustomizado.class, CampoCustomizadoResponseDTO.class)
				.<TipoCampoCustomizadoResponseDTO>addMapping(src -> src.getTipoCampoCustomizado(),
						(des, value) -> des.setTipoCampoCustomizadoResponseDTO(value));

		modelMapper.createTypeMap(Contato.class, ContatoResponseDTO.class)
				.<List<ContatoCampoCustomizadoResponseDTO>>addMapping(src -> src.getContatosCamposCustomizados(),
						(des, value) -> des.setCampoCustomizadoResponseDTOs(value));

		return modelMapper;

	}

}
