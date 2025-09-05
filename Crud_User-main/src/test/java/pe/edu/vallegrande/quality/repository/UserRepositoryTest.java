package pe.edu.vallegrande.quality.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.vallegrande.quality.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para UserRepository
 * Cobertura básica para generar reporte Jacoco
 */
class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void testSaveUser() {
        // Given
        User user = new User("123", "Juan", "juan@test.com", 25);

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertNotNull(savedUser);
        assertEquals("123", savedUser.getId());
        assertEquals("Juan", savedUser.getName());
    }

    @Test
    void testSaveNullUser() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userRepository.save(null);
        });
    }

    @Test
    void testSaveUserWithNullId() {
        // Given
        User user = new User(null, "Juan", "juan@test.com", 25);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    void testFindById() {
        // Given
        User user = new User("123", "Juan", "juan@test.com", 25);
        userRepository.save(user);

        // When
        Optional<User> found = userRepository.findById("123");

        // Then
        assertTrue(found.isPresent());
        assertEquals("Juan", found.get().getName());
    }

    @Test
    void testFindByIdNotFound() {
        // When
        Optional<User> found = userRepository.findById("999");

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByIdWithNullId() {
        // When
        Optional<User> found = userRepository.findById(null);

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll() {
        // Given
        User user1 = new User("123", "Juan", "juan@test.com", 25);
        User user2 = new User("456", "Maria", "maria@test.com", 30);
        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> users = userRepository.findAll();

        // Then
        assertEquals(2, users.size());
    }

    @Test
    void testDeleteById() {
        // Given
        User user = new User("123", "Juan", "juan@test.com", 25);
        userRepository.save(user);

        // When
        boolean deleted = userRepository.deleteById("123");

        // Then
        assertTrue(deleted);
        assertFalse(userRepository.findById("123").isPresent());
    }

    @Test
    void testDeleteByIdNotFound() {
        // When
        boolean deleted = userRepository.deleteById("999");

        // Then
        assertFalse(deleted);
    }

    @Test
    void testExistsById() {
        // Given
        User user = new User("123", "Juan", "juan@test.com", 25);
        userRepository.save(user);

        // When & Then
        assertTrue(userRepository.existsById("123"));
        assertFalse(userRepository.existsById("999"));
    }

    @Test
    void testFindByEmail() {
        // Given
        User user = new User("123", "Juan", "juan@test.com", 25);
        userRepository.save(user);

        // When
        Optional<User> found = userRepository.findByEmail("juan@test.com");

        // Then
        assertTrue(found.isPresent());
        assertEquals("Juan", found.get().getName());
    }

    @Test
    void testCount() {
        // Given
        User user1 = new User("123", "Juan", "juan@test.com", 25);
        User user2 = new User("456", "Maria", "maria@test.com", 30);
        userRepository.save(user1);
        userRepository.save(user2);

        // When
        long count = userRepository.count();

        // Then
        assertEquals(2, count);
    }

    @Test
    void testDeleteAll() {
        // Given
        User user1 = new User("123", "Juan", "juan@test.com", 25);
        User user2 = new User("456", "Maria", "maria@test.com", 30);
        userRepository.save(user1);
        userRepository.save(user2);

        // When
        userRepository.deleteAll();

        // Then
        assertEquals(0, userRepository.count());
    }
}
