package pe.edu.vallegrande.quality.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * REFACTORIZACIÓN: DTO para responses de usuarios
 * 
 * PROBLEMA ORIGINAL: Respuestas inconsistentes e inseguras
 * - return "ok", "fail" (Strings simples)
 * - return u.get() (exposición directa del modelo)
 * - return "not found" (respuestas no estructuradas)
 * 
 * MEJORA: DTO estructurado para respuestas
 * - Separación entre modelo interno y respuesta externa
 * - Respuestas consistentes y documentadas
 * - Control sobre qué información se expone
 * - Metadatos adicionales (timestamps, status)
 */
public class UserResponseDto {
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    @DeleteMapping("/del/{id}")
    public String delete(@PathVariable("id") String identifier){
        boolean ok = service.remove(identifier);
        if(ok){
            return "ok";
        }
        return "fail";
    }
    
    @GetMapping("/user/{id}")
    public Object getOne(@PathVariable String id){
        Optional<User> u = service.find(id);
        if(u.isPresent()){
            return u.get(); // Exposición directa del modelo
        }
        return "not found";
    }
    ========================================================= */
    
    // REFACTORIZACIÓN: Campos controlados para respuesta
    // Mejora: Control sobre la información expuesta al cliente
    private String id;
    private String name;
    private String email;
    private Integer age;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    // REFACTORIZACIÓN: Constructor por defecto
    // Mejora: Requerido para serialización JSON
    public UserResponseDto() {
        this.createdAt = LocalDateTime.now();
    }
    
    // REFACTORIZACIÓN: Constructor con parámetros principales
    // Mejora: Creación rápida desde el modelo User
    public UserResponseDto(String id, String name, String email, Integer age) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // REFACTORIZACIÓN: Getters y Setters
    // Mejora: Encapsulación apropiada para DTO de respuesta
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    // REFACTORIZACIÓN: toString para debugging
    // Mejora: Facilita el debugging y logging
    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                '}';
    }
}