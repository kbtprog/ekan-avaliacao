package com.ekan.avaliacao.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ekan.avaliacao.exception.InternalServerErrorException;
import com.ekan.avaliacao.exception.ResourceNotFoundException;
import com.ekan.avaliacao.model.dto.BeneficiarioDTO;
import com.ekan.avaliacao.model.dto.DocumentoDTO;
import com.ekan.avaliacao.model.dto.input.BeneficiarioInputDTO;
import com.ekan.avaliacao.model.dto.input.BeneficiarioUpdateDTO;
import com.ekan.avaliacao.model.dto.input.DocumentoInputDTO;
import com.ekan.avaliacao.service.BeneficiarioService;

@ExtendWith(MockitoExtension.class)
public class BeneficiarioControllerTest {
	
	@InjectMocks
	private BeneficiarioController controller;
	
	@Mock
	private BeneficiarioService service;

	private static BeneficiarioInputDTO inputDTO;
	private static BeneficiarioUpdateDTO updateDTO;
	private static DocumentoInputDTO documentoInputDTO;
	private static List<DocumentoInputDTO> listDocumentoInput;
	
	private static BeneficiarioDTO emptyDTO;
	private static BeneficiarioDTO filledDTO;
	private static List<BeneficiarioDTO> listBeneficiarioEmpty;
	private static List<BeneficiarioDTO> listBeneficiarioFilled;
	private static DocumentoDTO documentoDTO;
	private static List<DocumentoDTO> listDocumentoDTO;
	
	@BeforeAll
	public static void init() {
		
		LocalDate date = LocalDate.now();
		LocalDateTime dateTime = LocalDateTime.now();
		
		documentoInputDTO = new DocumentoInputDTO("tipoDocumento", "descricao");
		listDocumentoInput = new ArrayList<DocumentoInputDTO>();
		listDocumentoInput.add(documentoInputDTO);
		
		emptyDTO = new BeneficiarioDTO();
		updateDTO = new BeneficiarioUpdateDTO("nome", "telefone", date);
		inputDTO = new BeneficiarioInputDTO("nome", "telefone", listDocumentoInput, date); 
		filledDTO = new BeneficiarioDTO(1L, "nome", "telefone", date, dateTime, dateTime, listDocumentoDTO);
		
		documentoDTO = new DocumentoDTO(1L, "tipoDocumento", "descricao", dateTime, dateTime, 1L);
		listDocumentoDTO = new ArrayList<DocumentoDTO>();
		listDocumentoDTO.add(documentoDTO);

		listBeneficiarioEmpty = new ArrayList<BeneficiarioDTO>();		
		listBeneficiarioFilled = new ArrayList<BeneficiarioDTO>();
		listBeneficiarioFilled.add(filledDTO);
	}
	
	@Test
	void getAllBeneficiariosTest() {

		ResponseEntity<List<BeneficiarioDTO>> result = new ResponseEntity<List<BeneficiarioDTO>>(listBeneficiarioEmpty, HttpStatus.OK);
		
		when(service.getAllBeneficiarios()).thenReturn(listBeneficiarioEmpty);		
		assertEquals(result, controller.getAllBeneficiarios());
	}
	
	@Test
	void insereBeneficiarioSucess() throws InternalServerErrorException {
		
		ResponseEntity<BeneficiarioDTO> result = new ResponseEntity<BeneficiarioDTO>(filledDTO, HttpStatus.CREATED);
		
		when(service.insertBeneficiario(inputDTO)).thenReturn(filledDTO);
		assertEquals(result, controller.insertBeneficiario(inputDTO));
	}
	
	@Test
	void insereBeneficiarioFail() {
		
		ResponseEntity<BeneficiarioDTO> result = new ResponseEntity<BeneficiarioDTO>(emptyDTO, HttpStatus.CREATED);
		
		try {
			when(service.insertBeneficiario(inputDTO)).thenReturn(null);
			when(controller.insertBeneficiario(inputDTO)).thenThrow(InternalServerErrorException.class);
			assertEquals(result, controller.insertBeneficiario(inputDTO));
		} catch (InternalServerErrorException e) {
			assertEquals(InternalServerErrorException.class, e.getClass());
		}
	}
	
	@Test
	void updateBeneficiarioSucess() throws ResourceNotFoundException {
		
		ResponseEntity<BeneficiarioDTO> result = new ResponseEntity<BeneficiarioDTO>(filledDTO, HttpStatus.OK);
		
		when(service.updateBeneficiario(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(BeneficiarioInputDTO.class))).thenReturn(filledDTO);
		assertEquals(result, controller.updateBeneficiario(1L, updateDTO));
	}
	
	@Test
	void updateBeneficiarioFail() {		
		try {
			when(controller.updateBeneficiario(1L, updateDTO)).thenReturn(null);
			when(service.updateBeneficiario(1L, inputDTO)).thenReturn(null);
			controller.updateBeneficiario(1L, updateDTO);
		} catch (ResourceNotFoundException e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
		}
	}
	
	@Test
	void deleteBeneficiarioSucess() throws ResourceNotFoundException {
		ResponseEntity<String> result = new ResponseEntity<String>("Beneficiario e seus respectivos documentos excluidos com sucesso", HttpStatus.OK);
		
		when(service.deleteBeneficiario(any())).thenReturn(true);
		assertEquals(result, controller.deleteBeneficiario(filledDTO.getId()));
	}
	
	@Test
	void deleteBeneficiarioFail() {
		ResponseEntity<BeneficiarioDTO> result = new ResponseEntity<BeneficiarioDTO>(emptyDTO, HttpStatus.OK);
		
		try {
			when(service.deleteBeneficiario(any())).thenReturn(false);
			assertEquals(result, controller.deleteBeneficiario(any()));
		} catch (ResourceNotFoundException e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
		}
	}
}