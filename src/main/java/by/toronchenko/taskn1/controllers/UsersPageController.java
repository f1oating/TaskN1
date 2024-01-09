package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/users")
public class UsersPageController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }
}
