package pe.edu.vallegrande.quality.exception;

/**
 * REFACTORIZACIÓN: Excepción específica para errores de validación
 * 
 * PROBLEMA ORIGINAL: Validaciones manuales con RuntimeException genérica
 * - throw new RuntimeException("name required")
 * - throw new RuntimeException("email invalid")
 * - Manejo inconsistente de errores de validación
 * 
 * MEJORA: Excepción específica para validaciones
 * - Separación semántica de errores de validación
 * - Facilita el manejo diferenciado de errores
 * - Mejor experiencia de usuario con mensajes específicos
 */
public class UserValidationException extends RuntimeException {
    
    /**
     * Constructor con mensaje de validación
     * Mejora: Mensajes específicos de validación
     * @param message Descripción del error de validación
     */
    public UserValidationException(String message) {
        super(message);
    }
    
    /**
     * Constructor con campo y mensaje específico
     * Mejora: Información contextual del campo que falló
     * @param field Campo que falló en la validación
     * @param message Descripción del error
     */
    public UserValidationException(String field, String message) {
        super(String.format("Error en campo '%s': %s", field, message));
    }
    
    /**
     * Constructor completo con mensaje y causa
     * Mejora: Preservación de la cadena de excepciones
     * @param message Mensaje descriptivo
     * @param cause Causa raíz del error
     */
    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}