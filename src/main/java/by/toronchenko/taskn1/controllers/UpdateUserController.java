package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/updateuser")
public class UpdateUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/updateuser/{id}")
    public String updateUserPage(@PathVariable Long id, Model model){
        User user = userService.findUserById(id).get();
        model.addAttribute("user", user);
        return "updateuser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            @RequestParam String name, @RequestParam String password, @PathVariable Long id
    ){
        User user = userService.findUserById(id).get();
        user.setName(name);
        user.setPassword(password);
        userService.saveUser(user);

        return "redirect:/users";
    }
}
