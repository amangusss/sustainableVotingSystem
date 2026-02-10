package com.github.amangusss.sustainable_voting_system.controller;

import com.github.amangusss.sustainable_voting_system.dto.SimpleMessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SimpleMessageResponse> handleBadRequest(IllegalArgumentException ex,
                                                                  HttpServletRequest request) {
        log.warn("Bad request path={} message={}", request.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new SimpleMessageResponse(ex.getMessage()));
    }
}
