package com.durgesh.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.durgesh.errorresponse.RestApiErrorResponseMessage;

@RestController
@ControllerAdvice
public class GlobalExceptionHander {
	
	
	@ExceptionHandler(value=NoBrandExistExdeption.class)
	public ResponseEntity<RestApiErrorResponseMessage> NoProductFoundException(){
		
		RestApiErrorResponseMessage error=new RestApiErrorResponseMessage(400,"No Brand Found", new Date());
		
		return new   ResponseEntity<RestApiErrorResponseMessage>(error,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value=CustomerNameNotFoundExdeption.class)
	public ResponseEntity<RestApiErrorResponseMessage> customerNameNotFoundExdeption(){
		
		RestApiErrorResponseMessage error=new RestApiErrorResponseMessage(400,"Customer Name Not Found ", new Date());
		
		return new   ResponseEntity<RestApiErrorResponseMessage>(error,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value=OrderNotFoundException.class)
	public ResponseEntity<RestApiErrorResponseMessage> orderNotFoundException(){
		
		RestApiErrorResponseMessage error=new RestApiErrorResponseMessage(400,"Order Not Found ", new Date());
		
		return new   ResponseEntity<RestApiErrorResponseMessage>(error,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value=IdNotExistException.class)
	public ResponseEntity<RestApiErrorResponseMessage> idNotExistException(){
		
		RestApiErrorResponseMessage error=new RestApiErrorResponseMessage(400,"Id Not Exist ", new Date());
		
		return new   ResponseEntity<RestApiErrorResponseMessage>(error,HttpStatus.NOT_FOUND);
		
	}
	
	
	
	
}
