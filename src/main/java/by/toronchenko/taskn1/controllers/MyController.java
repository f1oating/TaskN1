package by.toronchenko.taskn1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/my-page")
    public String myPage(Model model) {
        model.addAttribute("message", "Привет, это моя страничка!");
        return "my-page";
    }

}
