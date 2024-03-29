package com.niewhic.vetclinic.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorMessage> doctorNotFoundException(DoctorNotFoundException ex, HttpServletRequest request) {
        return createErrorResponse(NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorMessage> patientNotFoundException(PatientNotFoundException ex, HttpServletRequest request) {
        return createErrorResponse(NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<ErrorMessage> appointmentNotFoundException(AppointmentNotFoundException ex, HttpServletRequest request) {
        return createErrorResponse(NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(OfficeNotFoundException.class)
    public ResponseEntity<ErrorMessage> officeNotFoundException(OfficeNotFoundException ex, HttpServletRequest request) {
        return createErrorResponse(NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(DateIsBusyException.class)
    public ResponseEntity<ErrorMessage> dateIsBusyException(DateIsBusyException ex, HttpServletRequest request) {
        return createErrorResponse(BAD_REQUEST, ex.getMessage(), request);
    }

    private ResponseEntity<ErrorMessage> createErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .code(status.value())
                .status(status.getReasonPhrase())
                .message(message)
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build(), status);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> noSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .code(NOT_FOUND.value())
                .status(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build(), NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return createErrorResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .code(UNAUTHORIZED.value())
                .status(UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .build(), UNAUTHORIZED);
    }

}
