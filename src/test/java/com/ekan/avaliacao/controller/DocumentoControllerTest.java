package com.ekan.avaliacao.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ekan.avaliacao.exception.ResourceNotFoundException;
import com.ekan.avaliacao.model.dto.DocumentoDTO;
import com.ekan.avaliacao.service.DocumentoService;

@ExtendWith(MockitoExtension.class)
public class DocumentoControllerTest {

	@InjectMocks
	private DocumentoController controller;
	
	@Mock
	private DocumentoService service;
	
	@Test
	void getAllByBeneficiarioIdNotFoundTest() {
		try {
			when(controller.getAllByBeneficiarioId(anyLong())).thenThrow(ResourceNotFoundException.class);
			controller.getAllByBeneficiarioId(anyLong());
		} catch (ResourceNotFoundException e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
		}
	}	
	
	@Test
	void getAllByBeneficiarioIdFoundTest() throws ResourceNotFoundException {
		
		DocumentoDTO documentoDTO = new DocumentoDTO(1L, "teste", "teste", LocalDateTime.now(), LocalDateTime.now(), 1L);
		List<DocumentoDTO> listDocumentoDTO = new ArrayList<DocumentoDTO>();
		listDocumentoDTO.add(documentoDTO);

		when(service.getAllByBeneficiarioId(anyLong())).thenReturn(listDocumentoDTO);		
		assertNotNull(controller.getAllByBeneficiarioId(anyLong()));
	}	
}