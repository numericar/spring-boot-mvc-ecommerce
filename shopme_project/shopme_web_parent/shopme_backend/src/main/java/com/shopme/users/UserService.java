package com.shopme.users;

import org.springframework.stereotype.Service;

import com.shopme.commons.entities.Role;
import com.shopme.commons.entities.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Iterable<User> listAll() {
        return this.userRepository.findAll();
    }

    public Iterable<Role> listRoles() {
        return this.roleRepository.findAll();
    }

    public void save(User user) {
        this.userRepository.save(user);
    }
}
