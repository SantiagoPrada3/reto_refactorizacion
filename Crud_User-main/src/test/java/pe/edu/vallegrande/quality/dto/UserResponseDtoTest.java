package pe.edu.vallegrande.quality.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para UserResponseDto
 * Cobertura básica para generar reporte Jacoco
 */
class UserResponseDtoTest {

    @Test
    void testDefaultConstructor() {
        // When
        UserResponseDto dto = new UserResponseDto();

        // Then
        assertNotNull(dto);
        assertNotNull(dto.getCreatedAt());
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getEmail());
        assertNull(dto.getAge());
    }

    @Test
    void testConstructorWithParameters() {
        // Given
        String id = "123";
        String name = "Juan Perez";
        String email = "juan@test.com";
        Integer age = 25;

        // When
        UserResponseDto dto = new UserResponseDto(id, name, email, age);

        // Then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        assertEquals(email, dto.getEmail());
        assertEquals(age, dto.getAge());
        assertNotNull(dto.getCreatedAt());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        UserResponseDto dto = new UserResponseDto();
        LocalDateTime now = LocalDateTime.now();

        // When
        dto.setId("456");
        dto.setName("Maria Garcia");
        dto.setEmail("maria@test.com");
        dto.setAge(30);
        dto.setCreatedAt(now);

        // Then
        assertEquals("456", dto.getId());
        assertEquals("Maria Garcia", dto.getName());
        assertEquals("maria@test.com", dto.getEmail());
        assertEquals(30, dto.getAge());
        assertEquals(now, dto.getCreatedAt());
    }

    @Test
    void testToString() {
        // Given
        UserResponseDto dto = new UserResponseDto("123", "Juan", "juan@test.com", 25);

        // When
        String toString = dto.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("Juan"));
        assertTrue(toString.contains("juan@test.com"));
        assertTrue(toString.contains("25"));
    }
}
