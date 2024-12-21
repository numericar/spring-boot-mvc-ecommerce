package com.shopme.user.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.commons.entities.Role;
import com.shopme.commons.entities.User;
import com.shopme.users.UserRepository;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // ทำให้ทุก ๆ method จะใช้งานทรัพยากรณ์เดียวกัน
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    private UserRepository repo;
    private TestEntityManager entityManager;
    private Integer userId = 1;

    @Autowired
    public UserRepositoryTest(UserRepository repo, TestEntityManager entityManager) {
        this.repo = repo;
        this.entityManager = entityManager;
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        Role adminRole = this.entityManager.find(Role.class, 1);
        Role assistantRole = this.entityManager.find(Role.class, 5);

        User user = new User("user1@gmail.com", "123456789", "Chanokchon", "Wongjampa");
        user.addRole(adminRole);
        user.addRole(assistantRole);

        User userSaved = this.repo.save(user);

        this.userId = userSaved.getId();

        assertTrue(userSaved.getId() > 0, "User id should greater than 0");
    }

    @Test
    @Order(2)
    public void testListAllUsers() {
        Iterable<User> users = this.repo.findAll();

        int count = 0;
        for (User user : users) {
            System.out.println(user.toString());
            count++;
        }

        assertTrue(count > 0, "User count should greater than 0");
    }

    @Test
    @Order(3)
    public void testGetUserById() {
        Optional<User> userOptional = this.repo.findById(this.userId);

        System.out.println(this.userId);

        assertTrue(userOptional.isPresent(), "User should not be null");
    }

    @Test
    @Order(4)
    public void testUpdateUser() {
        User user = this.repo.findById(this.userId).get();
        user.setEnabled(true);

        User userUpdated = this.repo.save(user);

        assertTrue(userUpdated.isEnabled() == true, "User enabled should be true");
    }

    @Test 
    @Order(5)
    public void testUpdateUserRoles() {
        Role adminRole = this.entityManager.find(Role.class, 1);

        User user = this.repo.findById(this.userId).get();
        user.getRoles().remove(adminRole);

        this.repo.save(user);
    }

    @Test
    public void testDeleteUser() {
        this.repo.deleteById(this.userId);

        this.repo.findById(this.userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "mint.colorfuls@gmail.com";

        Optional<User> userOptional = this.repo.getUserByEmail(email);

        assertTrue(userOptional.isPresent(), "User should not be null");
    }
}
