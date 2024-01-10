package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/createcompany")
public class CreateCompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/createcompany")
    public String createUserPage(Model model){
        return "createcompany";
    }

    @PostMapping("/createcompany/new")
    public String createUser(
            @RequestParam String name
    ){
        Company company = Company.builder()
                .name(name)
                .build();
        companyService.saveCompany(company);
        return "redirect:/users";
    }
}
