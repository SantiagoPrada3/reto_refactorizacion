package pe.edu.vallegrande.quality.dto;

import jakarta.validation.constraints.*;

/**
 * REFACTORIZACIÓN: DTO para requests de usuarios
 * 
 * PROBLEMA ORIGINAL: Uso de Map genérico para recibir datos
 * - @RequestBody Map payload (sin tipos, sin validaciones)
 * - String name = (String) payload.get("name") (casting manual)
 * - Validaciones manuales en el controller
 * 
 * MEJORA: DTO tipado con validaciones declarativas
 * - Tipos seguros y explícitos
 * - Validaciones automáticas con @Valid
 * - Separación entre modelo interno y API externa
 * - Mejor documentación de la API
 */
public class UserRequestDto {
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    @PostMapping("/createUserNow")
    public Object b(@RequestBody Map payload){
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        Integer age = payload.get("age") == null ? null : (Integer) payload.get("age");
        if(name == null || name.equals("")){
            return "name is required";
        }
    ========================================================= */
    
    // REFACTORIZACIÓN: Campos tipados con validaciones Jakarta
    // Mejora: Validaciones declarativas en lugar de manuales
    @NotBlank(message = "Nombre es obligatorio")
    @Size(min = 2, max = 50, message = "Nombre debe tener entre 2 y 50 caracteres")
    private String name;
    
    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Email debe tener formato válido")
    private String email;
    
    @NotNull(message = "Edad es obligatoria")
    @Min(value = 0, message = "Edad debe ser mayor o igual a 0")
    @Max(value = 120, message = "Edad debe ser menor o igual a 120")
    private Integer age;
    
    // REFACTORIZACIÓN: Constructor por defecto
    // Mejora: Requerido para deserialización JSON
    public UserRequestDto() {
    }
    
    // REFACTORIZACIÓN: Constructor con parámetros
    // Mejora: Facilita la creación en tests y otros contextos
    public UserRequestDto(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // REFACTORIZACIÓN: Getters y Setters
    // Mejora: Encapsulación apropiada para DTO
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
    
    // REFACTORIZACIÓN: toString para debugging
    // Mejora: Facilita el debugging y logging
    @Override
    public String toString() {
        return "UserRequestDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}