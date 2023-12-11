package com.ekan.avaliacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends Exception {

	private static final long serialVersionUID = 4714921862036864135L;

	public InternalServerErrorException(String message){
        super(message);
    }
}