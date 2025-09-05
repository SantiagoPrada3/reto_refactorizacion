package pe.edu.vallegrande.quality.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase User
 * Cobertura básica para generar reporte Jacoco
 */
class UserTest {

    @Test
    void testUserCreation() {
        // Given
        String id = "123";
        String name = "Juan Perez";
        String email = "juan@example.com";
        Integer age = 25;

        // When
        User user = new User(id, name, email, age);

        // Then
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(age, user.getAge());
    }

    @Test
    void testDefaultConstructor() {
        // When
        User user = new User();

        // Then
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getAge());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        User user = new User();

        // When
        user.setId("456");
        user.setName("Maria Garcia");
        user.setEmail("maria@example.com");
        user.setAge(30);

        // Then
        assertEquals("456", user.getId());
        assertEquals("Maria Garcia", user.getName());
        assertEquals("maria@example.com", user.getEmail());
        assertEquals(30, user.getAge());
    }

    @Test
    void testEquals() {
        // Given
        User user1 = new User("123", "Juan", "juan@test.com", 25);
        User user2 = new User("123", "Pedro", "pedro@test.com", 30);
        User user3 = new User("456", "Juan", "juan@test.com", 25);

        // Then
        assertEquals(user1, user2); // Mismo ID
        assertNotEquals(user1, user3); // Diferente ID
        assertNotEquals(user1, null);
        assertEquals(user1, user1); // Mismo objeto
    }

    @Test
    void testHashCode() {
        // Given
        User user1 = new User("123", "Juan", "juan@test.com", 25);
        User user2 = new User("123", "Pedro", "pedro@test.com", 30);

        // Then
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        // Given
        User user = new User("123", "Juan", "juan@test.com", 25);

        // When
        String toString = user.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("123"));
        assertTrue(toString.contains("Juan"));
        assertTrue(toString.contains("juan@test.com"));
        assertTrue(toString.contains("25"));
    }
}
