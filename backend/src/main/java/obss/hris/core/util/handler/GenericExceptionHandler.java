package obss.hris.core.util.handler;

import io.jsonwebtoken.ExpiredJwtException;
import obss.hris.exception.HumanResourceNotFoundException;
import obss.hris.exception.JobPostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", "Lütfen giriş yapınız.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", "Giriş Bilgileri Yanlış");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }

    @ExceptionHandler(JobPostNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleJobPostNotFoundException(JobPostNotFoundException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseMap);
    }

    @ExceptionHandler(HumanResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleHumanResourceNotFoundException(HumanResourceNotFoundException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", "İnsan kaynakları bulunamadı.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseMap);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseMap);
    }
}