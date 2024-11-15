package vn.com.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.com.iuh.fit.backend.repositories.UserRepository;
import vn.com.iuh.fit.backend.services.UserService;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/user-list";
    }
}
