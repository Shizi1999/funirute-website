package com.poly.exception;

import com.poly.payload.response.APIResponse;
import com.poly.payload.response.FailureAPIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFountException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFountException ex) {
        return ResponseEntity.status(400).body(ex);
    }

    @ExceptionHandler(CloudinaryException.class)
    public ResponseEntity<?> handleCloudinaryException(CloudinaryException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<?> handleTokenException(TokenException ex) {
        return ResponseEntity.status(401).body(new APIResponse(ex.getMessage(), false, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse("Method Argument Not Valid", false, null));
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleUnauthorizedError(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new FailureAPIResponse(ex.getMessage()));
    }
}
