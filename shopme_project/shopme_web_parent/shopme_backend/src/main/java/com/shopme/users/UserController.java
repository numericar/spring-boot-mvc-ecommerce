package com.shopme.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.commons.entities.Role;
import com.shopme.commons.entities.User;

@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewIndexPage(Model model) {
        Iterable<User> users = this.userService.listAll();

        model.addAttribute("users", users);

        return "users/users_index";
    }

    @GetMapping("/create")
    public String viewCreatePage(Model model) {
        User user = new User();
        Iterable<Role> roles = this.userService.listRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "users/users_create";
    }


    // RedirectAttribute ใช้สำหรับเพิ่มข้อมูลในการ redirect ไปที่หน้าอื่น
    @PostMapping("/create")
    public String createUserHandler(User user, RedirectAttributes redirectAttributes) {
        System.out.println(user.toString());

        this.userService.save(user);

        redirectAttributes.addFlashAttribute("message", "The user has been created succesfully");

        return "redirect:/users";
    }
}
