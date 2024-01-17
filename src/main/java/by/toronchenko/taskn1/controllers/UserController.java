package by.toronchenko.taskn1.controllers;

import by.toronchenko.taskn1.dto.UserDto;
import by.toronchenko.taskn1.entity.Company;
import by.toronchenko.taskn1.entity.User;
import by.toronchenko.taskn1.service.CompanyService;
import by.toronchenko.taskn1.service.UserService;
import by.toronchenko.taskn1.util.exception.NoCompanyFoundException;
import by.toronchenko.taskn1.util.exception.NoUserFoundException;
import by.toronchenko.taskn1.validators.UserValidator;
import by.toronchenko.taskn1.validators.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user_name", user.getName());
        model.addAttribute("company_name", user.getCompany().getName());
        return "user";
    }

    @GetMapping("/users")
    public String showUsers(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "") String name, Model model){
        Page<User> users = userService.findPageUsersByName(PageRequest.of(page, 5), name);
        model.addAttribute("users", users);
        model.addAttribute("maxPages", userService.pageSlicer(users.getNumber(), users.getTotalPages()));
        model.addAttribute("name", name);
        return "users";
    }

    @GetMapping("/createUser")
    public String createUserPage(@RequestParam(required = false) List<Error> errors, Model model){
        model.addAttribute("companies", companyService.findAllCompanies());
        model.addAttribute("errors", errors);
        return "createUser";
    }

    @PostMapping("/createUser")
    public ModelAndView createUser(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam Long company
    ) {
        User user = User.builder()
                .name(name)
                .password(password)
                .company(companyService.findCompanyById(company))
                .build();
        UserDto userDto = userService.saveUser(user);
        return userDto.errors().isEmpty()? new ModelAndView("redirect:/user/users") : new ModelAndView("createUser")
                .addObject("errors", userDto.errors())
                .addObject("companies", companyService.findAllCompanies());
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserPage(@PathVariable Long id, @RequestParam(required = false) List<Error> errors, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("companies", companyService.findAllCompanies());
        model.addAttribute("errors", errors);
        return "updateUser";
    }

    @PostMapping("/updateUser/{id}")
    public ModelAndView updateUser(
            @RequestParam String name, @RequestParam String password, @PathVariable Long id, @RequestParam Long company
    ) {
        User user = userService.findUserById(id);
        Company userCompany = companyService.findCompanyById(company);
        user.setName(name);
        user.setPassword(password);
        user.setCompany(userCompany);
        UserDto userDto = userService.saveUser(user);

        return userDto.errors().isEmpty()? new ModelAndView("redirect:/user/users") : new ModelAndView("updateUser")
                .addObject("errors", userDto.errors())
                .addObject("companies", companyService.findAllCompanies())
                .addObject("user", user);
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/user/users";
    }

}
