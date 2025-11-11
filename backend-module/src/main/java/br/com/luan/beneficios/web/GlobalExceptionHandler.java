package br.com.luan.beneficios.web;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    static class ApiError {
        public final OffsetDateTime timestamp = OffsetDateTime.now();
        public final int status;
        public final String error;
        public final String message;
        public final String path;

        ApiError(HttpStatus status, String message, String path) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
            this.path = path;
        }
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex,
                                                     org.springframework.web.context.request.ServletWebRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequest().getRequestURI()));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex,
                                                   org.springframework.web.context.request.ServletWebRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequest().getRequestURI()));
    }

    @ExceptionHandler({
            OptimisticLockException.class,
            OptimisticLockingFailureException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ApiError> handleConflict(Exception ex,
                                                   org.springframework.web.context.request.ServletWebRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(HttpStatus.CONFLICT, "Conflito de atualização. Tente novamente.", req.getRequest().getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex,
                                                   org.springframework.web.context.request.ServletWebRequest req) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("path", req.getRequest().getRequestURI());
        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> fields.put(fe.getField(), fe.getDefaultMessage()));
        body.put("fields", fields);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<ApiError> handleTx(TransactionSystemException ex,
                                             org.springframework.web.context.request.ServletWebRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, "Violação de regra/validação.", req.getRequest().getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex,
                                                  org.springframework.web.context.request.ServletWebRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado.", req.getRequest().getRequestURI()));
    }
}
