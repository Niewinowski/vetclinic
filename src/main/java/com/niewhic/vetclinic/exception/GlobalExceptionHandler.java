package com.niewhic.vetclinic.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

}
