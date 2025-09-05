
package pe.edu.vallegrande.quality;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * REFACTORIZACIÓN: Clase principal de la aplicación
 * 
 * PROBLEMA ORIGINAL: 
 * - System.out.println("App started...") - Mala práctica de logging
 * 
 * MEJORA:
 * - Uso de SLF4J para logging profesional
 * - Mensaje más informativo y profesional
 * - Configuración centralizada de logging
 */
@SpringBootApplication
public class VgQualityChallengeApplication {
    
    /* ========== CÓDIGO ORIGINAL (MAL IMPLEMENTADO) ==========
    public static void main(String[] args) {
        SpringApplication.run(VgQualityChallengeApplication.class, args);
        System.out.println("App started..."); // mala práctica: logging con System.out
    }
    ========================================================= */
    
    // REFACTORIZACIÓN: Logger apropiado en lugar de System.out.println
    private static final Logger logger = LoggerFactory.getLogger(VgQualityChallengeApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(VgQualityChallengeApplication.class, args);
        
        // REFACTORIZACIÓN: Logging profesional con mensaje informativo
        // Mejora: Uso de SLF4J con nivel INFO apropiado
        logger.info("=== Aplicación VG Quality Challenge iniciada exitosamente ===");
        logger.info("API REST disponible en: http://localhost:8080/api/v1/users");
        logger.info("Documentación de endpoints:");
        logger.info("  GET    /api/v1/users     - Listar todos los usuarios");
        logger.info("  POST   /api/v1/users     - Crear nuevo usuario");
        logger.info("  GET    /api/v1/users/{{id}} - Obtener usuario por ID");
        logger.info("  PUT    /api/v1/users/{{id}} - Actualizar usuario");
        logger.info("  DELETE /api/v1/users/{{id}} - Eliminar usuario");
    }
}
