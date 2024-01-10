package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.CompanyService;
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
    @Autowired
    private CompanyService companyService;

    @GetMapping("/updateuser/{id}")
    public String updateUserPage(@PathVariable Long id, Model model){
        User user = userService.findUserById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("companies", companyService.findAllCompanies());
        return "updateuser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            @RequestParam String name, @RequestParam String password, @PathVariable Long id, @RequestParam Long company
    ){
        User user = userService.findUserById(id).get();
        Company userCompany = companyService.findCompanyById(company).get();
        user.setName(name);
        user.setPassword(password);
        user.setCompany(userCompany);
        userService.saveUser(user);

        return "redirect:/users";
    }
}
