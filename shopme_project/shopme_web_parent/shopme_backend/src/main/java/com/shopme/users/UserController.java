package com.shopme.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopme.commons.entities.User;

@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewIndex(Model model) {
        Iterable<User> users = this.userService.listAll();

        model.addAttribute("users", users);

        return "users/users_index";
    }

}
