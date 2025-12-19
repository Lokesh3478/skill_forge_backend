package org.msa.skillforge_backend.course.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ---------------- 404 Not Found ---------------- */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /* ---------------- 400 Bad Request (DTO validation) ---------------- */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> response = baseBody(HttpStatus.BAD_REQUEST);
        response.put("errors", fieldErrors);

        return ResponseEntity.badRequest().body(response);
    }

    /* ---------------- 409 Conflict (DB / business constraints) ---------------- */
    @ExceptionHandler({IllegalStateException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex) {
        String message = "Request violates data constraints";

        if (ex instanceof DataIntegrityViolationException dive && dive.getRootCause() != null) {
            // Extract root cause message for unique constraint violation
            String rootMessage = dive.getRootCause().getMessage();

            if (rootMessage.toLowerCase().contains("unique") || rootMessage.toLowerCase().contains("duplicate")) {
                message = "Duplicate entry violates unique constraint: " + rootMessage;
            } else {
                message = rootMessage;
            }
        }

        return buildResponse(HttpStatus.CONFLICT, message);
    }

    /* ---------------- 500 Internal Server Error ---------------- */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception ex) {
        ex.printStackTrace(); // Optional: log exception
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error");
    }

    /* ---------------- Helpers ---------------- */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = baseBody(status);
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }

    private Map<String, Object> baseBody(HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        return body;
    }
}
