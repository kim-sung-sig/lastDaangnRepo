package com.demo.daangn.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {

    @GetMapping("/")
    public String index(Model model){
        // model.addAttribute("id", "st");
        return "index";
    }

    @GetMapping("/api/status")
    @ResponseBody
    public boolean status(HttpSession session) {
        log.info("로그인 시도함 return {}", session.getAttribute("user") != null);
        return session.getAttribute("user") != null; // 로그인했으면 true 아니면 false
    }

    @GetMapping(value = "/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null)
			model.addAttribute("error", "error");
		if (logout != null)
			model.addAttribute("logout", "logout");
		return "login";
	}
    
}
