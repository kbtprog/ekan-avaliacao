package com.ekan.avaliacao.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekan.avaliacao.model.dto.BeneficiarioDTO;
import com.ekan.avaliacao.model.dto.DocumentoDTO;
import com.ekan.avaliacao.model.dto.input.BeneficiarioInputDTO;
import com.ekan.avaliacao.model.dto.input.DocumentoInputDTO;
import com.ekan.avaliacao.model.entity.Beneficiario;
import com.ekan.avaliacao.repository.BeneficiarioRepository;

@Service
public class BeneficiarioService {

	@Autowired
	private BeneficiarioRepository repository;
	
	@Autowired
	private DocumentoService documentoService;
	
	public List<BeneficiarioDTO> getAllBeneficiarios() {
		
		return repository.findAll()
				.stream()
				.map(beneficiario -> new BeneficiarioDTO(
								beneficiario.getId(), 
								beneficiario.getNome(), 
								beneficiario.getTelefone(), 
								beneficiario.getDataNascimento(), 
								beneficiario.getDataInclusao(), 
								beneficiario.getDataAtualizacao(), 
								documentoService.getAllByBeneficiarioId(beneficiario.getId())))
				.collect(Collectors.toList());
	}
	
	public BeneficiarioDTO insertBeneficiario(BeneficiarioInputDTO dto) {
		Beneficiario newBeneficiario = new Beneficiario();
		BeanUtils.copyProperties(dto, newBeneficiario);
		newBeneficiario.setDataInclusao(LocalDateTime.now());
		
		Beneficiario result = repository.save(newBeneficiario);
		List<DocumentoDTO> newDocumentosDTO = new ArrayList<DocumentoDTO>();
		
		for (DocumentoInputDTO documentoInput : dto.getDocumentos()) {
			DocumentoDTO documento = new DocumentoDTO(
					null, 
					documentoInput.getTipoDocumento(), 
					documentoInput.getDescricao(), 
					LocalDateTime.now(), 
					null, 
					result.getId());
			
			newDocumentosDTO.add(documentoService.saveDocumento(documento));
		}
		
		return new BeneficiarioDTO(
				result.getId(), 
				result.getNome(), 
				result.getTelefone(), 
				result.getDataNascimento(), 
				LocalDateTime.now(), 
				null, 
				newDocumentosDTO);
	}
	
	public BeneficiarioDTO updateBeneficiario(Long id, BeneficiarioInputDTO dto) {
		Optional<Beneficiario> checkBeneficiario = repository.findById(id);
		BeneficiarioDTO result = null;
		
		if (checkBeneficiario.isEmpty()) {
			return result;
		} else {			
			BeanUtils.copyProperties(dto, checkBeneficiario.get());
			checkBeneficiario.get().setDataAtualizacao(LocalDateTime.now());
			
			Beneficiario updatedBeneficiario = repository.save(checkBeneficiario.get());
			result = new BeneficiarioDTO();
			BeanUtils.copyProperties(updatedBeneficiario, result);
			
			result.setDocumentos(documentoService.getAllByBeneficiarioId(id));
			
			return result;
		}
	}
	
	public Boolean deleteBeneficiario(Long id) {

		Optional<Beneficiario> checkBeneficiario = repository.findById(id);

		
		if (checkBeneficiario.isEmpty()) {
			return false;
		}

		documentoService.deleteAllDocumentsByBeneficiarioId(id);
		repository.deleteById(id);
		
		return true;
	}
}