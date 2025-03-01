package com.unittest.springjwt.controllers;

import com.unittest.springjwt.models.User;
import com.unittest.springjwt.security.jwt.JwtUtils;
import com.unittest.springjwt.security.services.UserDetailsImpl;
import com.unittest.springjwt.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserService userService;


    /**
     * This is test using MockSecurityContext manually
     * */

    @Test
    public void testGetUsers_WithMockSecurityContext() throws Exception {
        UserDetails userDetails = new UserDetailsImpl(1L, "testuser", "testuser@example.com", "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);


        User mockUser = new User(1L, "Test User", "testuser@example.com");
        when(userService.get()).thenReturn(List.of(mockUser));
      //  when(userService.getUserByUsername("testuser")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("Test User")) // Changed from "name" to "username"
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));


    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")  // Mock authenticated user
    public void getUsers_ReturnsUserList_WhenUsersExist() throws Exception {
        User mockUser = new User(1L, "Test User", "testuser@example.com");

        when(userService.get()).thenReturn(List.of(mockUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1)) // Checks list size
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"));

        verify(userService, times(1)).get();
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER") // Mock authenticated user
    public void getUsers_ReturnsEmptyList_WhenNoUsersExist() throws Exception {
        when(userService.get()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Asserts empty list

        verify(userService, times(1)).get();
    }

}

