package com.demo.daangn.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    /**
     * 메인 페이지
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String index(HttpServletRequest req, Model model){
        model.addAttribute("navUrl", req.getRequestURI());
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

    /**
     * 회원 가입 페이지
     * @param model
     * @return
     */
    @GetMapping("/signup")
    public String signup(Model model){
        return "pages/login/join";
    }

    @GetMapping("/test1")
    public String test1(HttpServletRequest req, Model model) {
        model.addAttribute("navUrl", req.getRequestURI());
        return "pages/test1";
    }

    @GetMapping("/test2")
    public String test2(HttpServletRequest req, Model model) {
        model.addAttribute("navUrl", req.getRequestURI());
        return "pages/test2";
    }

    @GetMapping("/test2/{id}")
    public String test2Detail(HttpServletRequest req, Model model, @PathVariable("id") String id) {
        model.addAttribute("navUrl", "/test2");
        model.addAttribute("title", "title" + id);
        model.addAttribute("id", id);
        return "pages/test2Detail";
    }

    @GetMapping("/test3")
    public String test3(HttpServletRequest req, Model model) {
        model.addAttribute("navUrl", req.getRequestURI());
        return "pages/test3";
    }

    @GetMapping("/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}
