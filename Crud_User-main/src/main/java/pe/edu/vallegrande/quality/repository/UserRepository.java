
package pe.edu.vallegrande.quality.repository;

import pe.edu.vallegrande.quality.model.User;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * REFACTORIZACIÓN: Repository con mejores prácticas
 * 
 * PROBLEMAS ORIGINALES:
 * 1. Falta anotación @Repository
 * 2. Lista estática sin sincronización
 * 3. Exposición directa de la colección interna
 * 4. Acceso directo a campos públicos (u.id)
 * 5. Implementación ineficiente de búsqueda y eliminación
 * 
 * MEJORAS APLICADAS:
 * - Anotación @Repository para inyección de dependencias
 * - ConcurrentHashMap para thread-safety
 * - Encapsulación de la colección interna
 * - Uso de getters en lugar de acceso directo a campos
 * - Implementación eficiente con Streams API
 */
@Repository // REFACTORIZACIÓN: Anotación faltante para estereotipo de Spring
public class UserRepository {
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    // Lista en memoria sin sincronización ni persistencia real.
    private static List<User> l = new ArrayList<>();

    public List<User> getUsers(){
        return l; // Exposición directa de la lista interna.
    }
    ========================================================= */
    
    // REFACTORIZACIÓN: ConcurrentHashMap para thread-safety
    // Mejora: Thread-safe y mejor rendimiento para búsquedas
    private final Map<String, User> users = new ConcurrentHashMap<>();
    
    /**
     * REFACTORIZACIÓN: Método que no expone la colección interna
     * Mejora: Retorna copia defensiva en lugar de la colección original
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public User save(User u){
        // Reglas duplicadas, sin validación real.
        l.add(u);
        return u;
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Método save mejorado
     * Mejora: Validaciones apropiadas y manejo de actualizaciones
     */
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuario no puede ser null");
        }
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("ID de usuario no puede ser null o vacío");
        }
        
        users.put(user.getId(), user);
        return user;
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public Optional<User> findById(String id){
        for(User u : l){
            if(u.id != null && u.id.equals(id)){
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Búsqueda eficiente por ID
     * Mejora: O(1) en lugar de O(n) y uso de getters
     */
    public Optional<User> findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(id));
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public boolean delete(String id){
        // Borrado ineficiente.
        Iterator<User> it = l.iterator();
        while(it.hasNext()){
            User u = it.next();
            if(u.id != null && u.id.equals(id)){
                it.remove();
                return true;
            }
        }
        return false;
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Eliminación eficiente
     * Mejora: O(1) en lugar de O(n)
     */
    public boolean deleteById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return users.remove(id) != null;
    }
    
    /**
     * REFACTORIZACIÓN: Método adicional para verificar existencia
     * Mejora: Operación específica y eficiente
     */
    public boolean existsById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return users.containsKey(id);
    }
    
    /**
     * REFACTORIZACIÓN: Búsqueda por email (funcionalidad adicional útil)
     * Mejora: Funcionalidad extendida para validaciones de unicidad
     */
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return users.values().stream()
                .filter(user -> email.equalsIgnoreCase(user.getEmail()))
                .findFirst();
    }
    
    /**
     * REFACTORIZACIÓN: Método para obtener conteo total
     * Mejora: Información útil para paginación y estadísticas
     */
    public long count() {
        return users.size();
    }
    
    /**
     * REFACTORIZACIÓN: Método para limpiar todos los datos (útil para testing)
     * Mejora: Funcionalidad de limpieza para pruebas
     */
    public void deleteAll() {
        users.clear();
    }
}
