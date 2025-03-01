package com.unittest.springjwt.service;

import com.unittest.springjwt.models.User;
import com.unittest.springjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User mockUser1;
    private User mockUser2;

    @BeforeEach
    void setUp() {
        mockUser1 = new User(1L, "Test User 1", "test1@example.com");
        mockUser2 = new User(2L, "Test User 2", "test2@example.com");
    }

    @Test
    void get_ReturnsEmptyList_WhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> users = userService.get();

        assertNotNull(users);
        assertTrue(users.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void get_ReturnsUserList_WhenUsersExist() {
        List<User> userList = List.of(mockUser1, mockUser2);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.get();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(mockUser1, users.get(0));
        assertEquals(mockUser2, users.get(1));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByUsername_ReturnsEmptyOptional_WhenUserNotFound() {
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<User> user = userService.getUserByUsername(username);

        assertTrue(user.isEmpty());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByUsername_ReturnsUser_WhenUserExists() {
        String username = "Test User 1";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser1));

        Optional<User> user = userService.getUserByUsername(username);

        assertTrue(user.isPresent());
        assertEquals(mockUser1, user.get());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByUsername_HandlesNullUsernameGracefully() {
        when(userRepository.findByUsername(null)).thenReturn(Optional.empty());

        Optional<User> user = userService.getUserByUsername(null);

        assertTrue(user.isEmpty());
        verify(userRepository, times(1)).findByUsername(null);
    }
}
