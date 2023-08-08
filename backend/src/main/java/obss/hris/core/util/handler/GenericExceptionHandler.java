package obss.hris.core.util.handler;

import io.jsonwebtoken.ExpiredJwtException;
import obss.hris.exception.CandidateBannedException;
import obss.hris.exception.HumanResourceNotFoundException;
import obss.hris.exception.JobPostNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", "Bu iş ilanına zaten başvurdunuz.");
        return ResponseEntity.badRequest().body(errorResponseMap);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> objectBody = new LinkedHashMap<>();
        List<String> exceptionalErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage()).toList();
        objectBody.put("error_message", exceptionalErrors);
        return ResponseEntity.badRequest().body(objectBody);
    }
    @ExceptionHandler(CandidateBannedException.class)
    public ResponseEntity<Map<String, String>> handleCandidateBannedException(CandidateBannedException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put("error_message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }

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