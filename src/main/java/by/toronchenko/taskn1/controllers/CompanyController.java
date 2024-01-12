package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.dto.CompanyDto;
import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.service.CompanyService;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.validators.CompanyValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public String showUsers(Model model){
        model.addAttribute("companies", companyService.findAllCompanies());
        return "companies";
    }

    @GetMapping("/createCompany")
    public String createUserPage(@RequestParam(required = false) List<Error> errors, Model model){
        model.addAttribute("errors", errors);
        return "createCompany";
    }

    @PostMapping("/createCompany")
    public ModelAndView createUser(
            @RequestParam String name
    ){
        Company company = Company.builder()
                .name(name)
                .build();
        CompanyDto companyDto = companyService.saveCompany(company);
        return companyDto.errors().isEmpty()? new ModelAndView("redirect:/company/companies") : new ModelAndView("createCompany")
                .addObject("errors", companyDto.errors());
    }

    @GetMapping("/updateCompany/{id}")
    public String updateUserPage(@PathVariable Long id, @RequestParam(required = false) List<Error> errors, Model model) throws NoCompanyFoundException {
        Company company = companyService.findCompanyById(id);
        model.addAttribute("errors", errors);
        model.addAttribute("company", company);
        return "updateCompany";
    }

    @PostMapping("/updateCompany/{id}")
    public ModelAndView updateUser(
            @RequestParam String name, @PathVariable Long id
    ) throws NoCompanyFoundException {
        Company company = companyService.findCompanyById(id);
        company.setName(name);
        CompanyDto companyDto = companyService.saveCompany(company);

        return companyDto.errors().isEmpty()? new ModelAndView("redirect:/company/companies") : new ModelAndView("updateCompany")
                .addObject("errors", companyDto.errors())
                .addObject("company", company);
    }

    @GetMapping("/deleteCompany/{id}")
    public String deleteUser(@PathVariable Long id) throws NoCompanyFoundException {
        companyService.deleteCompanyById(id);
        return "redirect:/company/companies";
    }

}
