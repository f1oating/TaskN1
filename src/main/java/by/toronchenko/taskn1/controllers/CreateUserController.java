package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.CompanyService;
import by.toronchenko.taskn1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/createuser")
public class CreateUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;

    @GetMapping("/createuser")
    public String createUserPage(Model model){
        return "createuser";
    }

    @PostMapping("/createuser/new")
    public String createUser(
            @RequestParam String name,
            @RequestParam String password
    ){
        User user = User.builder()
                .name(name)
                .password(password)
                .company(companyService.findCompanyById(1L).get())
                .build();
        userService.saveUser(user);
        return "redirect:/users";
    }
}
