package org.zainabed.projects.translation.config;



import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
//@EnableWebMvc
public class ResourceConstraintConfig {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input provided")
	public void constraintExceptionWithStatus() {
		System.out.println("************* Inside Exception handler *******************");
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input provided")
	public void transactionExceptionWithStatus() {
		System.out.println("************* Inside Transaction handler *******************");
	}
}
