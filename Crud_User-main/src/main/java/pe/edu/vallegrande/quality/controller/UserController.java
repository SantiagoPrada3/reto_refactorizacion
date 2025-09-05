
package pe.edu.vallegrande.quality.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.quality.constants.UserConstants;
import pe.edu.vallegrande.quality.dto.UserRequestDto;
import pe.edu.vallegrande.quality.dto.UserResponseDto;
import pe.edu.vallegrande.quality.mapper.UserMapper;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REFACTORIZACIÓN: Controller con mejores prácticas REST
 * 
 * PROBLEMAS ORIGINALES:
 * 1. Nombres crípticos de métodos (a(), b())
 * 2. Rutas inconsistentes (/listAll, /createUserNow, /del/{id})
 * 3. Inyección por campo sin @Autowired
 * 4. Validaciones en el controller (violación SRP)
 * 5. Respuestas no tipadas (Object, String)
 * 6. Manejo manual de errores con try/catch
 * 7. System.out.println para logging
 * 8. Sin versionado de API
 * 9. Uso de Map genérico para requests
 * 10. Exposición directa del modelo
 * 
 * MEJORAS APLICADAS:
 * - Nombres descriptivos y convenciones REST
 * - Rutas consistentes y versionadas
 * - Inyección por constructor
 * - ResponseEntity<T> para respuestas tipadas
 * - DTOs para separar API de modelo interno
 * - Delegación de validaciones al service
 * - Logging apropiado con SLF4J
 * - Manejo de errores centralizado
 */
@RestController
@RequestMapping(UserConstants.USERS_BASE_PATH) // REFACTORIZACIÓN: Rutas versionadas y centralizadas
public class UserController {
    
    // REFACTORIZACIÓN: Logger apropiado en lugar de System.out.println
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    // Inyección por campo (mala práctica).
    @SuppressWarnings("all")
    public UserService service = new UserService();
    ========================================================= */
    
    // REFACTORIZACIÓN: Inyección por constructor (mejor práctica)
    private final UserService userService;
    private final UserMapper userMapper;
    
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    @GetMapping("/listAll")
    public List<User> a(){
        // Lógica de presentación mezclada con negocio
        System.out.println("Getting users..."); // mala práctica de logging
        List<User> users = service.getAll();
        if(users == null){
            users = new ArrayList<>();
        }
        // Validación innecesaria y duplicada
        if(users.size() == 0){
            return users;
        }
        return users;
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Endpoint para listar todos los usuarios
     * Mejora: Nombre descriptivo, ResponseEntity, DTOs y logging apropiado
     * 
     * GET /api/v1/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        logger.info("Solicitando lista de todos los usuarios");
        
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userDtos = users.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
        
        logger.info("Retornando {} usuarios", userDtos.size());
        return ResponseEntity.ok(userDtos);
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    @PostMapping("/createUserNow")
    public Object b(@RequestBody Map payload){
        // Validaciones dentro del controller, tipos sin genéricos
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        Integer age = payload.get("age") == null ? null : (Integer) payload.get("age");
        if(name == null || name.equals("")){
            return "name is required"; // Respuesta no tipada
        }
        User u = new User(null, name, email, age);
        try{
            return service.create(u);
        }catch(Exception e){
            e.printStackTrace(); // mala práctica
            return e.getMessage();
        }
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Endpoint para crear un nuevo usuario
     * Mejora: DTO tipado, validaciones automáticas, ResponseEntity
     * 
     * POST /api/v1/users
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequest) {
        logger.info("Creando nuevo usuario: {}", userRequest.getName());
        
        User user = userMapper.toEntity(userRequest);
        User createdUser = userService.createUser(user);
        UserResponseDto responseDto = userMapper.toResponseDto(createdUser);
        
        logger.info("Usuario creado exitosamente con ID: {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    @GetMapping("/user/{id}")
    public Object getOne(@PathVariable String id){
        Optional<User> u = service.find(id);
        if(u.isPresent()){
            return u.get();
        }
        return "not found";
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Endpoint para obtener un usuario por ID
     * Mejora: Nombre descriptivo, ResponseEntity, DTO y manejo de errores delegado
     * 
     * GET /api/v1/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String id) {
        logger.info("Solicitando usuario con ID: {}", id);
        
        User user = userService.getUserById(id); // Lanza excepción si no existe
        UserResponseDto responseDto = userMapper.toResponseDto(user);
        
        return ResponseEntity.ok(responseDto);
    }
    
    /**
     * REFACTORIZACIÓN: Endpoint para actualizar un usuario
     * Mejora: Funcionalidad faltante con validaciones y DTOs
     * 
     * PUT /api/v1/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String id, 
            @Valid @RequestBody UserRequestDto userRequest) {
        
        logger.info("Actualizando usuario con ID: {}", id);
        
        User userToUpdate = userMapper.toEntity(userRequest);
        User updatedUser = userService.updateUser(id, userToUpdate);
        UserResponseDto responseDto = userMapper.toResponseDto(updatedUser);
        
        return ResponseEntity.ok(responseDto);
    }
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    @DeleteMapping("/del/{id}")
    public String delete(@PathVariable("id") String identifier){
        boolean ok = service.remove(identifier);
        if(ok){
            return "ok";
        }
        return "fail";
    }
    ========================================================= */
    
    /**
     * REFACTORIZACIÓN: Endpoint para eliminar un usuario
     * Mejora: Ruta consistente, ResponseEntity y manejo apropiado
     * 
     * DELETE /api/v1/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        logger.info("Eliminando usuario con ID: {}", id);
        
        userService.deleteUser(id); // Lanza excepción si no existe
        
        logger.info("Usuario eliminado exitosamente: {}", id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
