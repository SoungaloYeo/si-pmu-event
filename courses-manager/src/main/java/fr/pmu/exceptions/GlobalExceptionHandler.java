package fr.pmu.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(c -> FieldErrorVM.builder()
                        .message(c.getDefaultMessage())
                        .field(c.getField())
                        .objectName(c.getClass().getName()).build())
                .collect(Collectors.toList());

        var apiError = ApiError.builder()
                .status(status)
                .message("errors")
                .path(ErrorConstants.DEFAULT_TYPE.getPath())
                .errors(errors).build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var errors = ex.getSupportedHttpMethods()
                .stream()
                .map(method -> FieldErrorVM.builder()
                        .objectName(method.getClass().getName())
                        .build())
                .collect(Collectors.toList());

        var apiError = ApiError.builder()
                .status(status)
                .message("Method not supported")
                .path(ErrorConstants.DEFAULT_TYPE.getPath())
                .errors(errors).build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
