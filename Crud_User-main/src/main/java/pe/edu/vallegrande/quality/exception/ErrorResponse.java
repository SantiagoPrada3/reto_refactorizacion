package pe.edu.vallegrande.quality.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * REFACTORIZACIÓN: Respuesta estandarizada para errores
 * 
 * PROBLEMA ORIGINAL: Respuestas inconsistentes de errores
 * - return "not found" (String simple)
 * - return e.getMessage() (Object genérico)
 * - Falta de información contextual (timestamp, status, etc.)
 * 
 * MEJORA: Estructura consistente para todas las respuestas de error
 * - Información completa y estructurada
 * - Formato JSON consistente
 * - Facilita el debugging y logging
 */
public class ErrorResponse {
    
    // REFACTORIZACIÓN: Campos informativos para errores
    // Mejora: Información completa del error para debugging
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    private int status;
    private String error;
    private String message;
    private String path;
    
    /**
     * Constructor por defecto
     * Mejora: Timestamp automático para trazabilidad
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * Constructor completo
     * Mejora: Creación rápida con todos los datos necesarios
     * @param status Código de estado HTTP
     * @param error Tipo de error
     * @param message Mensaje descriptivo
     * @param path Ruta donde ocurrió el error
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    // REFACTORIZACIÓN: Getters y Setters
    // Mejora: Encapsulación adecuada
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}