package pe.edu.vallegrande.quality.mapper;

import pe.edu.vallegrande.quality.dto.UserRequestDto;
import pe.edu.vallegrande.quality.dto.UserResponseDto;
import pe.edu.vallegrande.quality.model.User;
import org.springframework.stereotype.Component;

/**
 * REFACTORIZACIÓN: Mapper para conversión entre DTOs y entidades
 * 
 * PROBLEMA ORIGINAL: Conversiones manuales y repetitivas
 * - User u = new User(null, name, email, age) (en controller)
 * - return u.get() (exposición directa del modelo)
 * - Lógica de conversión dispersa en controllers
 * 
 * MEJORA: Centralización de conversiones con mapper dedicado
 * - Separación de responsabilidades
 * - Reutilización de lógica de conversión
 * - Facilita mantenimiento y testing
 * - Aplicación del principio DRY
 */
@Component
public class UserMapper {
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    // En UserController.createUserNow():
    String name = (String) payload.get("name");
    String email = (String) payload.get("email");  
    Integer age = payload.get("age") == null ? null : (Integer) payload.get("age");
    User u = new User(null, name, email, age);
    
    // En UserController.getOne():
    return u.get(); // Exposición directa del modelo
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Conversión de DTO request a entidad
     * Mejora: Lógica centralizada y reutilizable de conversión
     * @param dto DTO con datos de entrada
     * @return Entidad User para persistencia
     */
    public User toEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }
        
        return new User(
            null, // ID se genera en el servicio
            dto.getName(),
            dto.getEmail(),
            dto.getAge()
        );
    }
    
    /**
     * REFACTORIZACIÓN: Conversión de entidad a DTO response
     * Mejora: Control sobre qué información se expone al cliente
     * @param user Entidad User
     * @return DTO para respuesta al cliente
     */
    public UserResponseDto toResponseDto(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAge()
        );
    }
    
    /**
     * REFACTORIZACIÓN: Actualización de entidad existente con datos del DTO
     * Mejora: Lógica reutilizable para operaciones de actualización
     * @param existingUser Usuario existente a actualizar
     * @param dto DTO con nuevos datos
     * @return Usuario actualizado
     */
    public User updateEntityFromDto(User existingUser, UserRequestDto dto) {
        if (existingUser == null || dto == null) {
            return existingUser;
        }
        
        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setAge(dto.getAge());
        
        return existingUser;
    }
}