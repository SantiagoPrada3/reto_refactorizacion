package pe.edu.vallegrande.quality.exception;

/**
 * REFACTORIZACIÓN: Excepción específica para usuario no encontrado
 * 
 * PROBLEMA ORIGINAL: Uso de RuntimeException genérica
 * - throw new RuntimeException("user is null")
 * - Pérdida de información semántica
 * - Dificultad para manejar casos específicos
 * 
 * MEJORA: Excepción específica y descriptiva
 * - Semántica clara del error
 * - Facilita el manejo específico de errores
 * - Mejora la trazabilidad de problemas
 */
public class UserNotFoundException extends RuntimeException {
    
    /**
     * Constructor con mensaje por defecto
     * Mejora: Mensaje estándar para casos simples
     */
    public UserNotFoundException() {
        super("Usuario no encontrado");
    }
    
    /**
     * Constructor con mensaje personalizado
     * Mejora: Flexibilidad para mensajes específicos
     * @param message Mensaje descriptivo del error
     */
    public UserNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor con ID del usuario no encontrado
     * Mejora: Información contextual específica
     * @param userId ID del usuario que no se encontró
     */
    public UserNotFoundException(String userId, boolean includeId) {
        super(includeId ? 
            String.format("Usuario con ID '%s' no encontrado", userId) : 
            "Usuario no encontrado");
    }
    
    /**
     * Constructor completo con mensaje y causa
     * Mejora: Preservación de la cadena de excepciones
     * @param message Mensaje descriptivo
     * @param cause Causa raíz del error
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}