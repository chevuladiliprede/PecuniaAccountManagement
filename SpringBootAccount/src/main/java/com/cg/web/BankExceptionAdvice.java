package com.cg.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.dto.AccountMessage;
import com.cg.exceptions.AccountException;
import com.cg.exceptions.AgeException;
import com.cg.exceptions.CustomerException;
import com.cg.util.AccountConstants;



@RestControllerAdvice
public class BankExceptionAdvice {

	Logger logger = LoggerFactory.getLogger(BankExceptionAdvice.class);

	
	@ExceptionHandler(value= {AccountException.class, CustomerException.class})
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public AccountMessage handleException2(Exception ex){
		logger.error(ex.getMessage());
		return new AccountMessage(ex.getMessage());
	}
	
	@ExceptionHandler(value= {DataIntegrityViolationException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason=AccountConstants.ID_ALREADY_EXISTS)
	public void handleException6(Exception ex){
		logger.error(ex.getMessage());
		
	}
	@ExceptionHandler(value= {AgeException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public AccountMessage handleException3(Exception ex){
		logger.error(ex.getMessage());
		return new AccountMessage(ex.getMessage());
	}
	
}
