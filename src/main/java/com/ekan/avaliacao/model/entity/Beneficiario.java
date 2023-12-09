package com.ekan.avaliacao.model.entity;

import java.time.LocalDate;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiario {

	@Id
	@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "dataNascimento")
	private LocalDate dataNascimento;
	
	@Column(name = "dataInclusao")
	private LocalDateTime dataInclusao;

	@Column(name = "dataAtualizacao")	
	private LocalDateTime dataAtualizacao;
	
	@Override
	public String toString() {
		return "Beneficiario{ "
				+ "Id: " + id + '\'' + ","  
				+ "Nome: " + nome + '\'' + ","
				+ "Telefone: " + telefone + '\'' + ","
				+ "Data Nascimento: " + dataNascimento + '\'' + ","
				+ "Data Inclusao: " + dataInclusao + '\'' + ","
				+ "Data Atualizacao: " + dataAtualizacao + '\'' + "}";
	}
}