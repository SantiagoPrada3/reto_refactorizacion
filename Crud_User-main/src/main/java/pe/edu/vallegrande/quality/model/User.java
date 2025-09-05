
package pe.edu.vallegrande.quality.model;

import jakarta.validation.constraints.*;
import java.util.Objects;

/**
 * REFACTORIZACIÓN DEL MODELO USER
 * 
 * PROBLEMAS IDENTIFICADOS EN EL CÓDIGO ORIGINAL:
 * 1. Campos públicos - Violación del principio de encapsulación
 * 2. Falta de validaciones - Sin anotaciones de validación
 * 3. Falta de métodos equals(), hashCode(), toString()
 * 4. Sin documentación adecuada
 */
public class User {
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    // Campos públicos (mala práctica).
    public String id;
    public String name;
    public String email;
    public Integer age;
    ========================================================= */
    
    // REFACTORIZACIÓN: Campos privados con validaciones Jakarta
    // Mejora: Encapsulación adecuada + validaciones declarativas
    @NotBlank(message = "ID no puede estar vacío")
    private String id;
    
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

    /* ========== CODIGO ORIGINAL (CONSTRUCTORES BASICOS) ==========
    // Falta validacion, equals/hashCode, toString, etc.
    public User(){}

    public User(String id, String name, String email, Integer age){
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
    ================================================================ */
    
    // REFACTORIZACIÓN: Constructores mejorados
    // Mejora: Constructor sin parámetros para frameworks
    public User() {
        // Constructor vacío requerido por Jackson y JPA
    }

    // Mejora: Constructor con todos los parámetros documentado
    /**
     * Constructor completo para crear un usuario
     * @param id Identificador único del usuario
     * @param name Nombre del usuario
     * @param email Email del usuario
     * @param age Edad del usuario
     */
    public User(String id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
    
    // REFACTORIZACIÓN: Getters y Setters (Encapsulación)
    // Mejora: Acceso controlado a los campos privados
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
    
    // REFACTORIZACIÓN: Métodos equals, hashCode y toString
    // Mejora: Implementación de métodos fundamentales para objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
