package com.shopme.users;

import org.springframework.stereotype.Service;

import com.shopme.commons.entities.User;

@Service
public class UserService {

    private UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> listAll() {
        return this.userRepository.findAll();
    }
}
