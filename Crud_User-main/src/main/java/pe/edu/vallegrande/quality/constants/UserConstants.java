package pe.edu.vallegrande.quality.constants;

/**
 * REFACTORIZACIÓN: Creación de constantes para eliminar valores mágicos
 * 
 * PROBLEMA ORIGINAL: Valores mágicos dispersos en el código
 * - age = 0 (valor por defecto sin explicación)
 * - Strings literales repetidos
 * - Números mágicos sin contexto
 * 
 * MEJORA: Centralización de constantes con nombres descriptivos
 */
public final class UserConstants {
    
    // REFACTORIZACIÓN: Constantes para valores por defecto
    // Mejora: Eliminación de valores mágicos con nombres descriptivos
    public static final Integer DEFAULT_AGE = 0;
    public static final String DEFAULT_SORT_FIELD = "name";
    
    // REFACTORIZACIÓN: Constantes para mensajes de error
    // Mejora: Centralización de mensajes para mantenibilidad
    public static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado";
    public static final String USER_CREATED_MESSAGE = "Usuario creado exitosamente";
    public static final String USER_UPDATED_MESSAGE = "Usuario actualizado exitosamente";
    public static final String USER_DELETED_MESSAGE = "Usuario eliminado exitosamente";
    
    // REFACTORIZACIÓN: Constantes para validaciones
    // Mejora: Parámetros de validación centralizados
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 120;
    
    // REFACTORIZACIÓN: Constantes para API paths
    // Mejora: URLs versionadas y consistentes
    public static final String API_VERSION = "/api/v1";
    public static final String USERS_BASE_PATH = API_VERSION + "/users";
    
    // REFACTORIZACIÓN: Constructor privado para evitar instanciación
    // Mejora: Clase utilitaria no instanciable
    private UserConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes y no debe ser instanciada");
    }
}