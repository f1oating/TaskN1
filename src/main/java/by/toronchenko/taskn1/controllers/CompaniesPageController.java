package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/companies")
public class CompaniesPageController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public String showUsers(Model model){
        model.addAttribute("companies", companyService.findAllCompanies());
        return "companies";
    }
}
