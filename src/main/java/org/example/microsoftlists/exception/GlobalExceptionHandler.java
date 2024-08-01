package org.example.microsoftlists.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.microsoftlists.view.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleIOException(Exception e, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(),request.getRequestURI()));
    }


}
