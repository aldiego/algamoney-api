package com.algaworks.algamoney.api.handler;

import com.algaworks.algamoney.api.service.exception.InactiveOrNonExistentPersonException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        var userMessage = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        var devMessage = ex.toString();

        var errors = createErrorList(Error.builder().devMessage(devMessage).userMessage(userMessage).build());
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        var userMessage = messageSource.getMessage("resource.not-supported-operation", null, LocaleContextHolder.getLocale());
        var devMessage = ExceptionUtils.getRootCauseMessage(ex);

        var errors = createErrorList(Error.builder().devMessage(devMessage).userMessage(userMessage).build());
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InactiveOrNonExistentPersonException.class})
    public ResponseEntity<Object> handleInactiveOrNonExistentPersonException(InactiveOrNonExistentPersonException ex) {
        var userMessage = messageSource.getMessage("person.inactive-or-non-existent", null, LocaleContextHolder.getLocale());
        var devMessage = ExceptionUtils.getRootCauseMessage(ex);

        var errors = createErrorList(Error.builder().devMessage(devMessage).userMessage(userMessage).build());
        return ResponseEntity.badRequest().body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var userMessage = messageSource.getMessage("invalid.message", null, LocaleContextHolder.getLocale());
        var devMessage = ex.getCause().toString();

        var errors = createErrorList(Error.builder().devMessage(devMessage).userMessage(userMessage).build());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var errors = createErrorList(ex.getBindingResult());

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> createErrorList(BindingResult bindingResult) {
        var errors = new ArrayList<Error>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String devMessage = fieldError.toString();
            errors.add(Error.builder().devMessage(devMessage).userMessage(userMessage).build());
        }

        return errors;
    }

    private List<Error> createErrorList(Error... errors) {
        return Arrays.asList(errors);
    }

    @Data
    @Builder
    public static class Error {

        private String userMessage;
        private String devMessage;


    }
}
