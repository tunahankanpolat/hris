package obss.hris.core.util.handler;

import obss.hris.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {
    private final static String ERROR_KEY = "error_message";
    @ExceptionHandler(CandidateAlreadyBannedException.class)
    public ResponseEntity<Map<String, String>> handleCandidateAlreadyBannedException(CandidateAlreadyBannedException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseMap);
    }
    @ExceptionHandler(JobPostNotActiveException.class)
    public ResponseEntity<Map<String, String>> handleJobPostNotActiveException(JobPostNotActiveException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseMap);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, "Bu iş ilanına zaten başvurdunuz.");
        return ResponseEntity.badRequest().body(errorResponseMap);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> objectBody = new LinkedHashMap<>();
        List<String> exceptionalErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage()).toList();
        objectBody.put(ERROR_KEY, exceptionalErrors);
        return ResponseEntity.badRequest().body(objectBody);
    }
    @ExceptionHandler(CandidateBannedException.class)
    public ResponseEntity<Map<String, String>> handleCandidateBannedException(CandidateBannedException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, "Lütfen giriş yapınız.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, "Giriş Bilgileri Yanlış");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMap);
    }

    @ExceptionHandler(JobPostNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleJobPostNotFoundException(JobPostNotFoundException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseMap);
    }

    @ExceptionHandler(HumanResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleHumanResourceNotFoundException(HumanResourceNotFoundException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, "İnsan kaynakları bulunamadı.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseMap);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> errorResponseMap = new HashMap<>();
        errorResponseMap.put(ERROR_KEY, e.getMessage());
        return ResponseEntity.badRequest().body(errorResponseMap);
    }
}