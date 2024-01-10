package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.service.CompanyService;
import by.toronchenko.taskn1.validators.CompanyValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("/updatecompany")
public class UpdateCompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyValidator companyValidator;

    @GetMapping("/updatecompany/{id}")
    public String updateUserPage(@PathVariable Long id, @RequestParam(required = false) List<Error> errors, Model model){
        Company company = companyService.findCompanyById(id).get();
        model.addAttribute("errors", errors);
        model.addAttribute("company", company);
        return "updatecompany";
    }

    @PostMapping("/updatecompany/{id}")
    public String updateUser(
            @RequestParam String name, @PathVariable Long id, Model model
    ){
        Company company = companyService.findCompanyById(id).get();
        company.setName(name);

        ValidationResult validationResult = companyValidator.isValid(company);
        if(validationResult.isValid()){
            model.addAttribute("errors", validationResult.getErrors());
            model.addAttribute("company", company);
            return "/updatecompany";
        }

        companyService.saveCompany(company);

        return "redirect:/companies";
    }
}

