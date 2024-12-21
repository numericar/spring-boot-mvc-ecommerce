package com.shopme.users;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.commons.entities.Role;
import com.shopme.commons.entities.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<User> listAll() {
        return this.userRepository.findAll();
    }

    public Iterable<Role> listRoles() {
        return this.roleRepository.findAll();
    }

    public void save(User user) {
        String passwordEncoded = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);

        this.userRepository.save(user);
    }

    public boolean isEmailUnique(String email) {
        Optional<User> user = this.userRepository.getUserByEmail(email);

        return user.isPresent();
    }
}
