package com.example.finance_tracker.handlers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleError(Exception e, WebRequest request) {
        System.out.checkError();
        System.out.println(e);
        Map<String, Object> errorDetails = new HashMap<>();
        HttpStatus status = determineHttpStatus(e);
        errorDetails.put("errorMessage", e.getMessage());
        errorDetails.put("errorCause", e.getCause() != null ? e.getCause().toString() : null);
        errorDetails.put("errorCode", determineErrorCode(status.value()));

        return ResponseHandler.generateResponse("Error occurred", status, null, errorDetails, null);
    }

    private HttpStatus determineHttpStatus(Exception e) {
        if (e instanceof ResponseStatusException) {
            return ((ResponseStatusException) e).getStatus();
        } else if (e instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        } else if (e instanceof IllegalStateException) {
            return HttpStatus.CONFLICT;
        } else if (e instanceof NoSuchElementException) {
            return HttpStatus.NOT_FOUND;
        } else if (e instanceof ConstraintViolationException) {
            return HttpStatus.BAD_REQUEST;
        } else if (e instanceof MethodArgumentNotValidException) {
            return HttpStatus.BAD_REQUEST;
        } else if (e instanceof HttpMessageNotReadableException) {
            return HttpStatus.BAD_REQUEST;
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (e instanceof HttpMediaTypeNotAcceptableException) {
            return HttpStatus.NOT_ACCEPTABLE;
        } else if (e instanceof MissingServletRequestParameterException) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    public String determineErrorCode(int status) {
        String prefix;
        int code;
        if (status >= 300 && status < 400) {
            prefix = "C";
            code = status - 300;
        } else if (status >= 400 && status < 500) {
            prefix = "D";
            code = status - 400;
        } else if (status >= 500 && status < 600) {
            prefix = "E";
            code = status - 500;
        } else {
            return "";
        }

        return prefix + String.format("%02d", code);
    }
}
