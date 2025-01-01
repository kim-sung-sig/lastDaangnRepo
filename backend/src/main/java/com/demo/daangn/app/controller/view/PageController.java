package com.demo.daangn.app.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    /**
     * 메인 페이지
     * @param model
     * @return
     */
    @GetMapping(value = {"/{path:^(?!api|css|js|img|components).*}/**", "/", "/index.html"})
    public String redirect() {
        return "pages/index";
    }

    /**
     * 로그인 페이지
     * @param model
     * @return
     */
    @GetMapping("/login")
    public String login(Model model, HttpSession session){
        if(session.getAttribute("user") != null){
            return "redirect:/";
        }
        return "pages/login/login";
    }
}
