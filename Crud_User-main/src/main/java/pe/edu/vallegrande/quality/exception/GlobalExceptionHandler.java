package pe.edu.vallegrande.quality.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * REFACTORIZACIÓN: Manejo centralizado de excepciones
 * 
 * PROBLEMA ORIGINAL: Manejo de errores disperso en controllers
 * - try/catch en cada método del controller
 * - e.printStackTrace() (mala práctica de logging)
 * - Respuestas inconsistentes (String, Object)
 * - Falta de logging estructurado
 * 
 * MEJORA: Centralización con @RestControllerAdvice
 * - Manejo consistente de errores en toda la aplicación
 * - Logging apropiado con SLF4J
 * - Respuestas estructuradas con ResponseEntity
 * - Separación de responsabilidades
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // REFACTORIZACIÓN: Logger apropiado en lugar de System.out.println
    // Mejora: Logging profesional con niveles y formateo
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * REFACTORIZACIÓN: Manejo específico para UserNotFoundException
     * Mejora: Respuesta HTTP 404 apropiada para recursos no encontrados
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {
        
        logger.warn("Usuario no encontrado: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "User Not Found",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    /**
     * REFACTORIZACIÓN: Manejo específico para UserValidationException
     * Mejora: Respuesta HTTP 400 apropiada para errores de validación
     */
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserValidationException(
            UserValidationException ex, WebRequest request) {
        
        logger.warn("Error de validación: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Error",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * REFACTORIZACIÓN: Manejo de errores de validación de Jakarta Validation
     * Mejora: Manejo apropiado de @Valid con mensajes detallados
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        logger.warn("Errores de validación: {}", errors);
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed",
            "Errores de validación en los campos: " + errors.toString(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * REFACTORIZACIÓN: Manejo de violaciones de constraints
     * Mejora: Manejo específico para validaciones de path variables
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {
        
        logger.warn("Violación de constraints: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Constraint Violation",
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * REFACTORIZACIÓN: Manejo genérico para excepciones no previstas
     * Mejora: Fallback seguro con logging de errores inesperados
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        // REFACTORIZACIÓN: Logger en lugar de printStackTrace()
        // Mejora: Logging estructurado para debugging
        logger.error("Error inesperado: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "Ocurrió un error interno en el servidor",
            request.getDescription(false).replace("uri=", "")
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}