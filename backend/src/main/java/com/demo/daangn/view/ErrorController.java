package com.demo.daangn.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String handleError(Model model){
        model.addAttribute("error", "error");
        return "error";
    }

    @GetMapping("/api/login")
    public String login(Model model){
        return "login";
    }

}
