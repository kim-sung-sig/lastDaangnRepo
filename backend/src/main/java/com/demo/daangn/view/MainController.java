package com.demo.daangn.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model){
        // model.addAttribute("id", "st");
        return "index";
    }

    @GetMapping("/error")
    public String handleError(Model model){
        model.addAttribute("error", "error");
        return "error";
    }

}
