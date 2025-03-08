package com.unittest.springjwt.repository;

import com.unittest.springjwt.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        // Arrange: Create a mock user and define repository behavior
        String username = "test";
        User mockUser = new User(1L, "Test User", "test@example.com");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        // Act: Call the method
        Optional<User> user = userRepository.findByUsername(username);

        // Assert: Verify the result
        assertTrue(user.isPresent());
        assertEquals("Test User", user.get().getUsername());
        assertEquals("test@example.com", user.get().getEmail());
    }
}
