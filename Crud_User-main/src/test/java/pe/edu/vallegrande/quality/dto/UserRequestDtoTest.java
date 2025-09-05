package pe.edu.vallegrande.quality.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para UserRequestDto
 * Cobertura básica para generar reporte Jacoco
 */
class UserRequestDtoTest {

    @Test
    void testDefaultConstructor() {
        // When
        UserRequestDto dto = new UserRequestDto();

        // Then
        assertNotNull(dto);
        assertNull(dto.getName());
        assertNull(dto.getEmail());
        assertNull(dto.getAge());
    }

    @Test
    void testConstructorWithParameters() {
        // Given
        String name = "Juan Perez";
        String email = "juan@test.com";
        Integer age = 25;

        // When
        UserRequestDto dto = new UserRequestDto(name, email, age);

        // Then
        assertEquals(name, dto.getName());
        assertEquals(email, dto.getEmail());
        assertEquals(age, dto.getAge());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        UserRequestDto dto = new UserRequestDto();

        // When
        dto.setName("Maria Garcia");
        dto.setEmail("maria@test.com");
        dto.setAge(30);

        // Then
        assertEquals("Maria Garcia", dto.getName());
        assertEquals("maria@test.com", dto.getEmail());
        assertEquals(30, dto.getAge());
    }

    @Test
    void testToString() {
        // Given
        UserRequestDto dto = new UserRequestDto("Juan", "juan@test.com", 25);

        // When
        String toString = dto.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("Juan"));
        assertTrue(toString.contains("juan@test.com"));
        assertTrue(toString.contains("25"));
    }
}
