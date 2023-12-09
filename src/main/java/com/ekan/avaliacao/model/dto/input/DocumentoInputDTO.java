package com.ekan.avaliacao.model.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocumentoInputDTO {

	private String tipoDocumento;	
	private String descricao;
}
