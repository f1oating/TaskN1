package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("/deletecompany")
public class DeleteCompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/deletecompany/{id}")
    public String deleteUser(@PathVariable Long id){
        companyService.deleteCompanyById(id);
        return "redirect:/companies";
    }

}
