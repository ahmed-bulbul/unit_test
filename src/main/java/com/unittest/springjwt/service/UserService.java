package com.unittest.springjwt.service;


import com.unittest.springjwt.models.User;
import com.unittest.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> get() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String mockUsername) {
        return userRepository.findByUsername(mockUsername);
    }
}
