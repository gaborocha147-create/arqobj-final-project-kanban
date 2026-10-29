package br.insper.edu.kanban.common;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice(basePackages = "br.insper.edu.kanban")
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponseDTO> resposta(HttpStatusCode status, String message, List<String> details) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setStatus(status.value());
        error.setMessage(message);
        error.setErrors(details);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "Erro de validação",
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage()).toList());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleStatus(ResponseStatusException ex) {
        return resposta(ex.getStatusCode(), ex.getReason(), List.of());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponseDTO> handleMalformedRequest(Exception ex) {
        return resposta(HttpStatus.BAD_REQUEST, "Dados da requisição inválidos", List.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(DataIntegrityViolationException ex) {
        return resposta(HttpStatus.CONFLICT, "Dados duplicados ou incompatíveis com os registros existentes", List.of());
    }
}
