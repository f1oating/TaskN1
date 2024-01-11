package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("/delete")
public class DeleteUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model){
        userService.deleteUserById(id);
        return "redirect:/users";
    }

}
