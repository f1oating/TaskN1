package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.service.CompanyService;
import by.toronchenko.taskn1.validators.CompanyValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("/createcompany")
public class CreateCompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyValidator companyValidator;

    @GetMapping("/createcompany")
    public String createUserPage(@RequestParam(required = false) List<Error> errors, Model model){
        model.addAttribute("errors", errors);
        return "createcompany";
    }

    @PostMapping("/createcompany")
    public String createUser(
            @RequestParam String name,
            Model model
    ){
        Company company = Company.builder()
                .name(name)
                .build();
        ValidationResult validationResult = companyValidator.isValid(company);
        if(validationResult.isValid()){
            model.addAttribute("errors", validationResult.getErrors());
            return "/createcompany";
        }
        companyService.saveCompany(company);
        return "redirect:/companies";
    }
}
