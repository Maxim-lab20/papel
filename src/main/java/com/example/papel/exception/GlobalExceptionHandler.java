package com.example.papel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred", ex);
        String bodyOfResponse = "An unexpected error occurred. Please try again later.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        log.error("User not found", ex);
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    protected ResponseEntity<Object> handleOptimisticLockingFailureException(OptimisticLockingFailureException ex, WebRequest request) {
        log.error("Optimistic locking failure occurred", ex);
        return handleExceptionInternal(ex, "Failed to update user due to optimistic locking conflict.",
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
