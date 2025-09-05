
package pe.edu.vallegrande.quality.service;

import pe.edu.vallegrande.quality.constants.UserConstants;
import pe.edu.vallegrande.quality.exception.UserNotFoundException;
import pe.edu.vallegrande.quality.exception.UserValidationException;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
 * REFACTORIZACIÓN: Service con mejores prácticas
 * 
 * PROBLEMAS ORIGINALES:
 * 1. Falta anotación @Service
 * 2. Inyección por campo sin @Autowired
 * 3. Instanciación manual del repository
 * 4. Validaciones manuales repetitivas
 * 5. Excepciones genéricas (RuntimeException)
 * 6. Valores mágicos (age = 0)
 * 7. Código duplicado de ordenamiento
 * 8. Acceso directo a campos públicos
 * 
 * MEJORAS APLICADAS:
 * - Anotación @Service e inyección por constructor
 * - Excepciones específicas y descriptivas
 * - Extracción de validaciones a métodos privados
 * - Uso de constantes en lugar de valores mágicos
 * - Logging apropiado
 * - Aplicación del principio DRY
 */
@Service // REFACTORIZACIÓN: Anotación faltante para estereotipo de Spring
public class UserService {
    
    // REFACTORIZACIÓN: Logger apropiado en lugar de System.out.println
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    // REFACTORIZACIÓN: Pattern para validación de email eficiente
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    // Inyección por campo (mala práctica) y sin @Service.
    public UserRepository repo = new UserRepository();
    ========================================================= */
    
    // REFACTORIZACIÓN: Inyección por constructor (mejor práctica)
    // Mejora: Inmutabilidad, facilita testing y cumple principios SOLID
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public List<User> getAll(){
        List<User> data = repo.getUsers();
        if(data == null){ // chequeo innecesario
            return new ArrayList<>();
        }
        // Duplicación de lógica: ordenamiento manual aquí
        Collections.sort(data, (a,b) -> a.name != null ? a.name.compareToIgnoreCase(b.name) : -1);
        return data;
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Obtener todos los usuarios ordenados
     * Mejora: Lógica simplificada y uso de método utilitario
     */
    public List<User> getAllUsers() {
        logger.debug("Obteniendo todos los usuarios");
        List<User> users = userRepository.findAll();
        return sortUsersByName(users);
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public User create(User u){
        if(u == null){
            throw new RuntimeException("user is null"); // excepciones genéricas
        }
        // Validaciones manuales repetidas
        if(u.name == null || u.name.trim().equals("")){
            throw new RuntimeException("name required");
        }
        if(u.email == null || !u.email.contains("@")){
            throw new RuntimeException("email invalid");
        }
        if(u.age == null){
            u.age = 0; // valor mágico
        }
        if(u.id == null || u.id.isEmpty()){
            u.id = UUID.randomUUID().toString();
        }
        return repo.save(u);
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Crear usuario con validaciones apropiadas
     * Mejora: Excepciones específicas, validaciones extraídas y logging
     */
    public User createUser(User user) {
        logger.info("Creando nuevo usuario");
        
        validateUserForCreation(user);
        
        // REFACTORIZACIÓN: Verificar unicidad de email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserValidationException("email", "Email ya está registrado");
        }
        
        // REFACTORIZACIÓN: Generar ID si no existe
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
            logger.debug("ID generado para nuevo usuario: {}", user.getId());
        }
        
        // REFACTORIZACIÓN: Asignar edad por defecto usando constante
        if (user.getAge() == null) {
            user.setAge(UserConstants.DEFAULT_AGE);
            logger.debug("Asignada edad por defecto: {}", UserConstants.DEFAULT_AGE);
        }
        
        User savedUser = userRepository.save(user);
        logger.info("Usuario creado exitosamente con ID: {}", savedUser.getId());
        return savedUser;
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public Optional<User> find(String id){
        return repo.findById(id);
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Buscar usuario por ID con logging
     * Mejora: Logging y manejo de casos edge
     */
    public User getUserById(String id) {
        logger.debug("Buscando usuario con ID: {}", id);
        
        if (id == null || id.trim().isEmpty()) {
            throw new UserValidationException("id", "ID no puede ser null o vacío");
        }
        
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException(id, true);
                });
    }
    
    /**
     * REFACTORIZACIÓN: Actualizar usuario existente
     * Mejora: Funcionalidad faltante con validaciones apropiadas
     */
    public User updateUser(String id, User updatedUser) {
        logger.info("Actualizando usuario con ID: {}", id);
        
        User existingUser = getUserById(id); // Reutiliza validación
        
        validateUserForUpdate(updatedUser);
        
        // REFACTORIZACIÓN: Verificar unicidad de email (excepto el usuario actual)
        userRepository.findByEmail(updatedUser.getEmail())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new UserValidationException("email", "Email ya está registrado");
                });
        
        // REFACTORIZACIÓN: Actualizar campos
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAge(updatedUser.getAge());
        
        User savedUser = userRepository.save(existingUser);
        logger.info("Usuario actualizado exitosamente: {}", savedUser.getId());
        return savedUser;
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public boolean remove(String id){
        return repo.delete(id);
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Eliminar usuario con validaciones
     * Mejora: Verificación de existencia y excepciones específicas
     */
    public void deleteUser(String id) {
        logger.info("Eliminando usuario con ID: {}", id);
        
        if (id == null || id.trim().isEmpty()) {
            throw new UserValidationException("id", "ID no puede ser null o vacío");
        }
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id, true);
        }
        
        boolean deleted = userRepository.deleteById(id);
        if (deleted) {
            logger.info("Usuario eliminado exitosamente: {}", id);
        } else {
            logger.error("Error al eliminar usuario: {}", id);
            throw new RuntimeException("Error interno al eliminar usuario");
        }
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    // Utilidad duplicada que debería extraerse o usarse de forma consistente
    public List<User> sortByName(List<User> users){
        Collections.sort(users, (x,y) -> x.name.compareToIgnoreCase(y.name));
        return users;
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Método utilitario unificado para ordenamiento
     * Mejora: Eliminación de duplicación de código
     */
    private List<User> sortUsersByName(List<User> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<User> sortedUsers = new ArrayList<>(users);
        sortedUsers.sort((a, b) -> {
            String nameA = a.getName() != null ? a.getName() : "";
            String nameB = b.getName() != null ? b.getName() : "";
            return nameA.compareToIgnoreCase(nameB);
        });
        
        return sortedUsers;
    }
    
    /**
     * REFACTORIZACIÓN: Validaciones extraídas para creación
     * Mejora: Reutilización y principio DRY
     */
    private void validateUserForCreation(User user) {
        if (user == null) {
            throw new UserValidationException("Usuario no puede ser null");
        }
        
        validateUserBasicFields(user);
    }
    
    /**
     * REFACTORIZACIÓN: Validaciones extraídas para actualización
     * Mejora: Reutilización y principio DRY
     */
    private void validateUserForUpdate(User user) {
        if (user == null) {
            throw new UserValidationException("Usuario no puede ser null");
        }
        
        validateUserBasicFields(user);
    }
    
    /**
     * REFACTORIZACIÓN: Validaciones básicas centralizadas
     * Mejora: Eliminación de duplicación de código de validación
     */
    private void validateUserBasicFields(User user) {
        // Validación de nombre
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new UserValidationException("name", "Nombre es obligatorio");
        }
        
        if (user.getName().length() < UserConstants.MIN_NAME_LENGTH || 
            user.getName().length() > UserConstants.MAX_NAME_LENGTH) {
            throw new UserValidationException("name", 
                String.format("Nombre debe tener entre %d y %d caracteres", 
                    UserConstants.MIN_NAME_LENGTH, UserConstants.MAX_NAME_LENGTH));
        }
        
        // REFACTORIZACIÓN: Validación de email mejorada
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new UserValidationException("email", "Email es obligatorio");
        }
        
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new UserValidationException("email", "Email debe tener formato válido");
        }
        
        // Validación de edad
        if (user.getAge() != null && 
            (user.getAge() < UserConstants.MIN_AGE || user.getAge() > UserConstants.MAX_AGE)) {
            throw new UserValidationException("age", 
                String.format("Edad debe estar entre %d y %d años", 
                    UserConstants.MIN_AGE, UserConstants.MAX_AGE));
        }
    }
}
