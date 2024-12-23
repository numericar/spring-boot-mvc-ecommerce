package com.shopme.users;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.commons.entities.Role;
import com.shopme.commons.entities.User;
import com.shopme.exceptions.UserNotFoundException;

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
        boolean isUpdateUser = user.getId() != null;

        if (isUpdateUser) {
            User existingUser = this.userRepository.findById(user.getId()).get();

            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                String passwordEncoded = this.passwordEncoder.encode(user.getPassword());
                user.setPassword(passwordEncoded);
            }
        } else {
            String passwordEncoded = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordEncoded);
        }

        this.userRepository.save(user);
    }

    public boolean isEmailUnique(String email, Integer userId) {
        Optional<User> user = this.userRepository.getUserByEmail(email, userId);

        return user.isPresent();
    }

    public Optional<User> findById(Integer userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);

        return userOptional;
    }

    public void delete(Integer id) {
        Long count = this.userRepository.countById(id);

        if (count <= 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        this.userRepository.deleteById(id);
    }
}
