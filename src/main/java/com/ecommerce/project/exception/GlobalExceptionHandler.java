package com.ecommerce.project.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception e, HttpServletRequest request){
        String message= "Something went wrong: "+ e.getMessage();
        ErrorMessage errorMessage= new ErrorMessage(message,request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> dataNotFoundExceptionHandler(DataNotFoundException e, HttpServletRequest request){
        ErrorMessage errorMessage= new ErrorMessage(e.getMessage(), request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        Map<String,String> map= new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(objectError -> {
            String errorName= objectError.getField();
            String value= objectError.getDefaultMessage();
            map.put(errorName,value);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> constraintViolationExceptionHandler(MethodArgumentNotValidException e){
        Map<String,String> map= new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(objectError -> {
            String errorName= objectError.getField();
            String value= objectError.getDefaultMessage();
            map.put(errorName,value);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }


}
