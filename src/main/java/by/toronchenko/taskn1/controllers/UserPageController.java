package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller("/user")
public class UserPageController {

    @Autowired
    UserService userService;

    @GetMapping("user/{id}")
    public String showUser(@PathVariable Long id, Model model){
        Optional<User> userOptional = userService.findUserById(id);
        User user = userOptional.get();
        model.addAttribute("user_name", user.getName());
        model.addAttribute("company_name", user.getCompany().getName());
        return "user";
    }
}
