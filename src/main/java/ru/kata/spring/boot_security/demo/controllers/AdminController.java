package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(Model model, @AuthenticationPrincipal User user) {
        User newUser = new User();
        model.addAttribute("users", userService.showAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("newUser", newUser);
        return "users/showAllUsers";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setRoles(userService.findRolesByName(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String role) {
        user.setRoles(userService.findRolesByName(role));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";

    }
}
