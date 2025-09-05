package pe.edu.vallegrande.quality.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pe.edu.vallegrande.quality.exception.UserNotFoundException;
import pe.edu.vallegrande.quality.exception.UserValidationException;
import pe.edu.vallegrande.quality.model.User;
import pe.edu.vallegrande.quality.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para UserService
 * Cobertura básica para generar reporte Jacoco
 */
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Given
        User user1 = new User("1", "Ana", "ana@test.com", 25);
        User user2 = new User("2", "Carlos", "carlos@test.com", 30);
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertEquals(2, result.size());
        assertEquals("Ana", result.get(0).getName()); // Ordenado alfabéticamente
        assertEquals("Carlos", result.get(1).getName());
    }

    @Test
    void testCreateUserSuccess() {
        // Given
        User user = new User(null, "Juan", "juan@test.com", 25);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User result = userService.createUser(user);

        // Then
        assertNotNull(result);
        assertNotNull(result.getId()); // ID debe ser generado
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUserWithNullUser() {
        // When & Then
        assertThrows(UserValidationException.class, () -> {
            userService.createUser(null);
        });
    }

    @Test
    void testCreateUserWithInvalidName() {
        // Given
        User user = new User(null, "", "juan@test.com", 25);

        // When & Then
        assertThrows(UserValidationException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void testCreateUserWithInvalidEmail() {
        // Given
        User user = new User(null, "Juan", "invalid-email", 25);

        // When & Then
        assertThrows(UserValidationException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void testCreateUserWithExistingEmail() {
        // Given
        User existingUser = new User("999", "Otro", "juan@test.com", 30);
        User newUser = new User(null, "Juan", "juan@test.com", 25);
        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(existingUser));

        // When & Then
        assertThrows(UserValidationException.class, () -> {
            userService.createUser(newUser);
        });
    }

    @Test
    void testGetUserByIdSuccess() {
        // Given
        User user = new User("123", "Juan", "juan@test.com", 25);
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        // When
        User result = userService.getUserById("123");

        // Then
        assertNotNull(result);
        assertEquals("Juan", result.getName());
    }

    @Test
    void testGetUserByIdNotFound() {
        // Given
        when(userRepository.findById("999")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById("999");
        });
    }

    @Test
    void testGetUserByIdWithNullId() {
        // When & Then
        assertThrows(UserValidationException.class, () -> {
            userService.getUserById(null);
        });
    }

    @Test
    void testUpdateUserSuccess() {
        // Given
        User existingUser = new User("123", "Juan", "juan@test.com", 25);
        User updatedUser = new User(null, "Juan Carlos", "juan.carlos@test.com", 26);
        
        when(userRepository.findById("123")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail("juan.carlos@test.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // When
        User result = userService.updateUser("123", updatedUser);

        // Then
        assertNotNull(result);
        assertEquals("Juan Carlos", result.getName());
        assertEquals("juan.carlos@test.com", result.getEmail());
    }

    @Test
    void testDeleteUserSuccess() {
        // Given
        when(userRepository.existsById("123")).thenReturn(true);
        when(userRepository.deleteById("123")).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> {
            userService.deleteUser("123");
        });
        verify(userRepository).deleteById("123");
    }

    @Test
    void testDeleteUserNotFound() {
        // Given
        when(userRepository.existsById("999")).thenReturn(false);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser("999");
        });
    }

    @Test
    void testDeleteUserWithNullId() {
        // When & Then
        assertThrows(UserValidationException.class, () -> {
            userService.deleteUser(null);
        });
    }
}
