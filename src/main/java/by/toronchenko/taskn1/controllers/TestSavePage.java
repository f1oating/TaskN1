package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestSavePage {

    @Autowired
    UserService userService;

    @GetMapping("/add")
    public String myPage(Model model) {
        var user = User.builder()
                .name("Dima")
                .password("28102006")
                .build();
        var company = Company.builder()
                .name("Ayonis")
                .build();
        user.setCompany(company);

        System.out.println(userService.saveUser(user));
        return "add";
    }
}
