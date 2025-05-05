package com.assovio.zapja.zapjaapi.domain.model;

import java.util.Base64;

import com.assovio.zapja.zapjaapi.domain.model.contracts.EntityBase;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "midia")
public class Midia extends EntityBase {

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "arquivo")
	private byte[] arquivo;

	@Column(name = "tipo")
	private String tipo;

	@Transient
	private String extensao;

	public String getExtensao() {
		String extensao = this.tipo.substring(this.tipo.lastIndexOf("/") + 1);
		return !extensao.isBlank() ? extensao : "";
	}

	public String getBase() {
		if (this.arquivo != null) {
			return "data:" + this.tipo + ";base64," + Base64.getEncoder().encodeToString(this.arquivo);
		}
		return null;
	}

}
