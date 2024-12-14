package com.demo.daangn.app.controller.view;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/api/user/status")
    @ResponseBody
    public Map<String, Boolean> userStatus(HttpSession session){
        if(session.getAttribute("user") != null){
            return Map.of("status", true);
        } else {
            return Map.of("status", false);
        }
    }

    @GetMapping("/test/test/chatConnect")
    public String chatConnect(){
        return "pages/test/chatRedisConnectionTest";
    }

    @GetMapping("/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}
