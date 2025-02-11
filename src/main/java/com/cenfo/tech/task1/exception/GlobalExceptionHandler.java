package com.cenfo.tech.task1.exception;


import com.cenfo.tech.task1.response.http.GlobalHandlerResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        logger.error("Entity not found: {}", ex.getMessage(), ex);
        return new GlobalHandlerResponse().handleResponse("Content not found", HttpStatus.NOT_FOUND, request);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handledMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.error(ex.getMessage(), ex);
        return new GlobalHandlerResponse().handleResponse("Invalid request data. Please check the provided fields and try again", HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        if (ex.getMessage().contains("Page index must be 0 or greater") || ex.getMessage().contains("Page size must be at least 1")) {
            logger.error("Invalid page/size parameters: {}", ex.getMessage(), ex);
            return new GlobalHandlerResponse().handleResponse("Invalid page or size parameters.", HttpStatus.BAD_REQUEST, request);
        }

        logger.error("Invalid argument: {}", ex.getMessage(), ex);
        return new GlobalHandlerResponse().handleResponse("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, HttpServletRequest request) {
        logger.error("Internal server error: {}", ex.getMessage(), ex);
        return new GlobalHandlerResponse().handleResponse("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> handleJsonMappingException(JsonMappingException ex, HttpServletRequest request) {
        logger.error("Json mapping error: {}", ex.getMessage());
        return new GlobalHandlerResponse().handleResponse("Invalid request data. Please ensure all fields are correct and follow the expected format.", HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        logger.error("Expired JWT error: {}", ex.getMessage());
        return new GlobalHandlerResponse().handleResponse("Token expired.", HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        logger.error("Error occurred: {}", ex.getMessage(), ex);
        return new GlobalHandlerResponse().handleResponse("An unexpected error occurred while registering the entity.", HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }
}
