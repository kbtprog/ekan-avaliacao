package com.ekan.avaliacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ekan.avaliacao.exception.InternalServerErrorException;
import com.ekan.avaliacao.model.dto.DocumentoDTO;
import com.ekan.avaliacao.model.entity.Documento;
import com.ekan.avaliacao.repository.DocumentoRepository;

@ExtendWith(MockitoExtension.class)
public class DocumentoServiceTest {

	@InjectMocks
	private DocumentoService service;
	
	@Mock
	private DocumentoRepository repository;
	
	private static Documento documento;
	private static List<Documento> filledDocumentoList;
	private static List<Documento> emptyDocumentoList;
	
	private static DocumentoDTO documentoDTO;
	private static List<DocumentoDTO> filledDocumentoDtoList;
	private static List<DocumentoDTO> emptyDocumentoDtoList;
	
	@BeforeAll
	public static void init() {
		LocalDateTime data = LocalDateTime.now();
		
		documento = new Documento(1L, "tipoDocumento", "descricao", data, data, 1L);
		emptyDocumentoList = new ArrayList<Documento>();
		filledDocumentoList = new ArrayList<Documento>();
		filledDocumentoList.add(documento);
		
		documentoDTO = new DocumentoDTO(1L, "tipoDocumento", "descricao", data, data, 1L);
		emptyDocumentoDtoList = new ArrayList<DocumentoDTO>();
		filledDocumentoDtoList = new ArrayList<DocumentoDTO>();
		filledDocumentoDtoList.add(documentoDTO);
		
	}
	
	@Test
	void saveDocumentoSucessTest() throws InternalServerErrorException {
		when(repository.save(ArgumentMatchers.any(Documento.class))).thenReturn(documento);		
		DocumentoDTO savedDocumento = service.saveDocumento(documentoDTO);
		assertNotNull(savedDocumento);
	}
	
	@Test
	void saveDocumentoSucessFail() {
		try {
			when(service.saveDocumento(null)).thenThrow(InternalServerErrorException.class);
			service.saveDocumento(null);
		} catch (InternalServerErrorException e) {
			assertEquals(InternalServerErrorException.class, e.getClass());
		}
	}
	
	@Test
	void getAllByBeneficiarioIdSucess() {
		when(repository.getAllByBeneficiarioId(1L)).thenReturn(filledDocumentoList);
		assertNotNull(service.getAllByBeneficiarioId(1L));
	}
	
	
	@Test
	void getAllByBeneficiarioIdFail() {
		when(repository.getAllByBeneficiarioId(1L)).thenReturn(emptyDocumentoList);
		assertEquals(emptyDocumentoDtoList, service.getAllByBeneficiarioId(1L));
	}
	
	@Test
	void deleteAllDocumentsByBeneficiarioId() {
		service.deleteAllDocumentsByBeneficiarioId(1L);
		verify(repository, times(1)).deleteAllDocumentsByBeneficiarioId(1L);
	}
}