package obss.hris.core.util.handler;

import obss.hris.exception.JobPostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }

    @ExceptionHandler(JobPostNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleJobPostNotFoundException(JobPostNotFoundException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseMap);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseMap);
    }
}