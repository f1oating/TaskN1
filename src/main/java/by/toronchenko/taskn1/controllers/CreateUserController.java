package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.CompanyService;
import by.toronchenko.taskn1.service.UserService;
import by.toronchenko.taskn1.validators.UserValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("/createuser")
public class CreateUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserValidator userValidator;

    @GetMapping("/createuser")
    public String createUserPage(@RequestParam(required = false) List<Error> errors, Model model){
        model.addAttribute("companies", companyService.findAllCompanies());
        model.addAttribute("errors", errors);
        return "createuser";
    }

    @PostMapping("/createuser")
    public String createUser(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam Long company,
            Model model
    ){
        User user = User.builder()
                .name(name)
                .password(password)
                .company(companyService.findCompanyById(company).get())
                .build();
        ValidationResult validationResult = userValidator.isValid(user);
        if(validationResult.isValid()){
            model.addAttribute("errors", validationResult.getErrors());
            model.addAttribute("companies", companyService.findAllCompanies());
            return "/createuser";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }
}
