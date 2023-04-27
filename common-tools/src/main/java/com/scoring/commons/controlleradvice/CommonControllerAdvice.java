package com.scoring.commons.controlleradvice;

import com.scoring.commons.exceptions.InitialChecksException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(value = {InitialChecksException.class
    })
    public ResponseEntity<?> handleBusinessExceptions(RuntimeException ex, WebRequest request, HandlerMethod handler) {
        log.error("Caught BUSINESS exception = {}", ex.getMessage(), ex);

        return new ResponseEntity<>("stub", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleExceptions(RuntimeException ex, WebRequest request, HandlerMethod handler) {
        log.error("Caught OTHER exception = {}", ex.getMessage());

        return new ResponseEntity<>("stub", HttpStatus.BAD_REQUEST);
    }
}
