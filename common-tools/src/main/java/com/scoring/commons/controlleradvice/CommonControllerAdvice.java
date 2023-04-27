package com.scoring.commons.controlleradvice;

import com.scoring.commons.exceptions.InitialChecksException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;

import java.util.Map;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class CommonControllerAdvice {

    private final CommonErrorAttributes commonErrorAttributes;

    @ExceptionHandler(value = {InitialChecksException.class})
    public ResponseEntity<Map<String, Object>> handleBusinessExceptions(RuntimeException ex,
                                                                        ServletWebRequest request,
                                                                        HandlerMethod handler) {
        log.error("Caught BUSINESS exception = {}", ex.getMessage(), ex);
        final ErrorAttributeOptions errorAttributeOptions = ErrorAttributeOptions.defaults().including(
                ErrorAttributeOptions.Include.MESSAGE);
        final Map<String, Object> errorAttributesMap = commonErrorAttributes.getErrorAttributes(request,
                errorAttributeOptions);

        return new ResponseEntity<>(errorAttributesMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Map<String, Object>> handleExceptions(RuntimeException ex,
                                                                WebRequest request,
                                                                HandlerMethod handler) {
        log.error("Caught OTHER exception = {}", ex.getMessage());
        final ErrorAttributeOptions errorAttributeOptions = ErrorAttributeOptions.defaults().including(
                ErrorAttributeOptions.Include.MESSAGE);
        final Map<String, Object> errorAttributesMap = commonErrorAttributes.getErrorAttributes(request,
                errorAttributeOptions);

        return new ResponseEntity<>(errorAttributesMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
