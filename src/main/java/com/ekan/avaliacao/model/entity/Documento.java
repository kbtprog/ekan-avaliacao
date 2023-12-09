package com.ekan.avaliacao.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Documento")
public class Documento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tipoDocumento")
	private String tipoDocumento;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "dataInclusao")
	private LocalDateTime dataInclusao;

	@Column(name = "dataAtualizacao")	
	private LocalDateTime dataAtualizacao;
	
	@Column(name = "idBeneficiario")
	private Long beneficiario;
	
	@Override
	public String toString() {
		return "Documento { "
				+ "Id: " + id + '\'' + ","
				+ "Tipo Documento: " + tipoDocumento + '\'' + ","
				+ "Data Inclusao: " + dataInclusao + '\'' + ","
				+ "Data Atualizacao: " + dataAtualizacao + '\'' + "}";
	}
}