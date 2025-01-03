package com.shopme.users;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.commons.entities.Role;
import com.shopme.commons.entities.User;
import com.shopme.exceptions.UserNotFoundException;

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
        model.addAttribute("pageTitle", "Create user");

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

    @GetMapping("/edit/{id}")
    public String editUser(Model model, RedirectAttributes redirectAttributes, @PathVariable(name = "id") Integer id) {
        try {
            Optional<User> userOptional = this.userService.findById(id);

            if (!userOptional.isPresent()) {
                throw new UserNotFoundException("Could not find any user with ID " + id);
            } else {
                Iterable<Role> roles = this.userService.listRoles();

                model.addAttribute("user", userOptional.get());
                model.addAttribute("roles", roles);
                model.addAttribute("pageTitle", "Update user");
            }

            return "users/users_create";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());

            return "redirect:/users";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String removeUser(RedirectAttributes redirectAttributes, @PathVariable("id") Integer userId) {
        try {
            this.userService.delete(userId);

            redirectAttributes.addFlashAttribute("message", "The user id " + userId + " has been deleted successfully");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/users";
    }
}
