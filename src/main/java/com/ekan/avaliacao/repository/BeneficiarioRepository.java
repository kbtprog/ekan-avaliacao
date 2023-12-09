package com.ekan.avaliacao.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ekan.avaliacao.model.entity.Beneficiario;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long>{

	@Modifying
	@Transactional
	@Query("  UPDATE Beneficiario b "
			+ 	"SET "
			+ 	" nome = :nome, "
			+ 	" telefone = :telefone, "
			+ 	" dataNascimento = :dataNascimento, "
			+ 	" dataInclusao = :dataInclusao, "
			+ 	" dataAtualizacao = :dataAtualizacao "
			+ 	"WHERE b.id = :id")
	void updateBeneficiario( 
			String nome, 
			String telefone, 
			LocalDate dataNascimento, 
			LocalDateTime dataInclusao, 
			LocalDateTime dataAtualizacao, 
			Long id);
}
