package br.com.project.wish.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@ControllerAdvice
@Validated
public class ApiExceptionHandler {
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  ErroView handleException(Exception ex) {
    return new ErroView(ex.getMessage());
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  ErroView resourceNotFoundException(ResourceNotFoundException resourceNotFoundException, HttpServletRequest request) {
    return new ErroView(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), resourceNotFoundException.getMessage(), request.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErroView methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
    String error = e.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage()).findFirst().orElseThrow();
    return new ErroView(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), "not valid due to validation error: " + error, request.getRequestURI());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErroView illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
    return new ErroView(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), e.getLocalizedMessage(), request.getRequestURI());
  }
}
