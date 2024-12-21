package com.shopme.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check_email")
    public String checkDuplicateEmail(@RequestParam(name = "email", required = true) String email) {
        return this.userService.isEmailUnique(email) ? "Duplicated" : "OK"; 
    }

}
