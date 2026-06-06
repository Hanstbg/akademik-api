package org.delcom.app.services;

import org.delcom.app.entities.User;
import org.delcom.app.entities.User.Role;
import org.delcom.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setName("Hans");
        mockUser.setEmail("hans@example.com");
        mockUser.setPassword("hashed");
        mockUser.setRole(Role.MAHASISWA);
    }

    @Test
    void testRegisterSuccess() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        User result = userService.register("Hans", "hans@example.com", "pass123", Role.MAHASISWA);
        assertNotNull(result);
        assertEquals(Role.MAHASISWA, result.getRole());
    }

    @Test
    void testRegisterDuplicateEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
            () -> userService.register("Hans", "hans@example.com", "pass", Role.MAHASISWA));
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("hans@example.com")).thenReturn(Optional.of(mockUser));
        assertTrue(userService.findByEmail("hans@example.com").isPresent());
    }

    @Test
    void testFindById() {
        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
        assertTrue(userService.findById(mockUser.getId()).isPresent());
    }

    @Test
    void testCheckPassword() {
        when(passwordEncoder.matches("plain", "hashed")).thenReturn(true);
        assertTrue(userService.checkPassword(mockUser, "plain"));
    }
}
