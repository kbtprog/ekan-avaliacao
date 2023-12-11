package com.ekan.avaliacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ekan.avaliacao.exception.InternalServerErrorException;
import com.ekan.avaliacao.model.dto.BeneficiarioDTO;
import com.ekan.avaliacao.model.dto.DocumentoDTO;
import com.ekan.avaliacao.model.dto.input.BeneficiarioInputDTO;
import com.ekan.avaliacao.model.dto.input.DocumentoInputDTO;
import com.ekan.avaliacao.model.entity.Beneficiario;
import com.ekan.avaliacao.model.entity.Documento;
import com.ekan.avaliacao.repository.BeneficiarioRepository;
import com.ekan.avaliacao.repository.DocumentoRepository;

@ExtendWith(MockitoExtension.class)
public class BeneficiarioServiceTest {

	@InjectMocks
	private BeneficiarioService service;	
	
	@Mock
	private BeneficiarioRepository repository;
	
	@Mock
	private DocumentoService documentoService;
	
	@Mock
	private DocumentoRepository documentoRepository;
	
	private static Beneficiario beneficiario;
	private static BeneficiarioDTO beneficiarioDTO;
	private static BeneficiarioInputDTO beneficiarioInputDto;
	private static BeneficiarioInputDTO beneficiarioInputWithoutDocumentListDto;
	private static List<Beneficiario> listBeneficiario;
	private static List<Beneficiario> emptyListBeneficiario;
	private static Optional<Beneficiario> optBeneficiarioPresent;
	private static Optional<Beneficiario> optBeneficiarioNotPresent;
	
	private static Documento documento;
	private static List<Documento> listDocumento;
	
	private static DocumentoDTO documentoDTO;
	private static List<DocumentoDTO> filledDocumentoDtoList;
	private static DocumentoInputDTO documentoInputDto;
	
	private static List<DocumentoInputDTO> filledDocumentoInputDtoList;
	
	@BeforeAll
	public static void init() {
		LocalDate date = LocalDate.now();
		LocalDateTime dateTime = LocalDateTime.now();
		
		emptyListBeneficiario = new ArrayList<Beneficiario>();
		
		beneficiario = new Beneficiario(1L, "nome", "telefone", date, dateTime, dateTime);
		listBeneficiario = new ArrayList<Beneficiario>();
		listBeneficiario.add(beneficiario);
		
		documento = new Documento(1L, "tipoDocumento", "descricao", dateTime, dateTime, 1L);
		listDocumento = new ArrayList<Documento>();
		listDocumento.add(documento);
		
		documentoDTO = new DocumentoDTO(1L, "tipoDocumento", "descricao", dateTime, dateTime, 1L);
		filledDocumentoDtoList = new ArrayList<DocumentoDTO>();
		filledDocumentoDtoList.add(documentoDTO);
		
		documentoInputDto = new DocumentoInputDTO("tipoDocumento", "descricao");
		filledDocumentoInputDtoList = new ArrayList<DocumentoInputDTO>();
		filledDocumentoInputDtoList.add(documentoInputDto);

		optBeneficiarioNotPresent = Optional.empty();
		optBeneficiarioPresent = Optional.of(beneficiario);
		beneficiarioInputDto = new BeneficiarioInputDTO("nome", "telefone", filledDocumentoInputDtoList, date);
		beneficiarioInputWithoutDocumentListDto = new BeneficiarioInputDTO("nome", "telefone", new ArrayList<DocumentoInputDTO>(), date);
		beneficiarioDTO = new BeneficiarioDTO(1L, "nome", "telefone", date, dateTime, dateTime, filledDocumentoDtoList);
	}
	
	@Test
	void getAllBeneficiariosSucess() throws InternalServerErrorException {

		when(documentoService.getAllByBeneficiarioId(1L)).thenReturn(filledDocumentoDtoList);
		when(repository.findAll()).thenReturn(listBeneficiario);
		
		assertNotNull(service.getAllBeneficiarios());
	}
	
	@Test
	void getAllBeneficiariosFail() {
		when(repository.findAll()).thenReturn(emptyListBeneficiario);
		assertEquals(emptyListBeneficiario, service.getAllBeneficiarios());
	}
	
	@Test
	void insertBeneficiarioSucess() throws InternalServerErrorException {
		
		when(repository.save(ArgumentMatchers.any(Beneficiario.class))).thenReturn(beneficiario);
		
		BeneficiarioDTO insertBeneficiario = service.insertBeneficiario(beneficiarioInputDto);
		assertNotNull(insertBeneficiario);
	}
	
	@Test
	void insertBeneficiarioFail() {
		
		try {
			when(service.insertBeneficiario(null)).thenThrow(InternalServerErrorException.class);
			service.insertBeneficiario(beneficiarioInputDto);
		} catch (InternalServerErrorException e) {
			assertEquals(InternalServerErrorException.class, e.getClass());
		}
	}
	
	@Test
	void insertBeneficiarioWithoutDocumentFail() {
		
		try {
			when(service.insertBeneficiario(beneficiarioInputWithoutDocumentListDto)).thenThrow(InternalServerErrorException.class);
			service.insertBeneficiario(beneficiarioInputWithoutDocumentListDto);
		} catch (InternalServerErrorException e) {
			assertEquals(InternalServerErrorException.class, e.getClass());
		}
	}
	
	@Test
	void updateBeneficiarioSucess() {
		
		when(repository.findById(1L)).thenReturn(optBeneficiarioPresent);
		when(repository.save(ArgumentMatchers.any(Beneficiario.class))).thenReturn(optBeneficiarioPresent.get());
		when(documentoService.getAllByBeneficiarioId(1L)).thenReturn(filledDocumentoDtoList);
		
		BeneficiarioDTO updateBeneficiario = service.updateBeneficiario(1L, beneficiarioInputDto);
		
		/*
		 *  Não foi implementado um assertEquals para a "Data de Atualização" porque ela é atualizada em 
		 *  tempo de execução, então ela nunca vai ser igual a uma data que eu faça o Mock nessa classe.
		 */
		assertEquals(beneficiarioDTO.getDataInclusao(), updateBeneficiario.getDataInclusao());
		assertEquals(beneficiarioDTO.getDataNascimento(), updateBeneficiario.getDataNascimento());
		assertEquals(beneficiarioDTO.getDocumentos(), updateBeneficiario.getDocumentos());
		assertEquals(beneficiarioDTO.getId(), updateBeneficiario.getId());
		assertEquals(beneficiarioDTO.getNome(), updateBeneficiario.getNome());
		assertEquals(beneficiarioDTO.getTelefone(), updateBeneficiario.getTelefone());	
	}
	
	@Test
	void updateBeneficiarioFail() {
		when(repository.findById(1L)).thenReturn(optBeneficiarioNotPresent);
		assertNull(service.updateBeneficiario(1L, beneficiarioInputDto));
	}
	
	@Test
	void deleteBeneficiarioSucess() {
		when(repository.findById(1L)).thenReturn(optBeneficiarioPresent);		
		
		service.deleteBeneficiario(1L);
		verify(repository, times(1)).deleteById(1L);
	}
	
	@Test
	void deleteBeneficiarioFail() {
		when(repository.findById(1L)).thenReturn(optBeneficiarioNotPresent);
		assertFalse(service.deleteBeneficiario(1L));
	}
}