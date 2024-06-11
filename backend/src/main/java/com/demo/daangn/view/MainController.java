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

    /** 로그인 페이지 */
    @GetMapping("/login")
    public String login(HttpSession session, Model model,
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout
    ){

        if(error != null && !error.isEmpty()){
            model.addAttribute("error", "error");
        }
        if(logout != null && !logout.isEmpty()){
            model.addAttribute("logout", "logout");
        }
        if(session.getAttribute("user") != null){
            return "redirect:/";
        }
        return "login";
    }

    /** 회원가입 주소 */
    @GetMapping(value = "/join")
	public String join(HttpSession session) {
		if (session.getAttribute("user") != null) { // 나쁜사람 방지
			session.removeAttribute("user");
			session.invalidate();
			return "redirect:/";
		}
		return "join";
	}

    @GetMapping("/api/status")
    @ResponseBody
    public boolean status(HttpSession session) {
        log.info("?? {}", session.getAttribute("user"));
        return session.getAttribute("user") != null; // 로그인했으면 true 아니면 false
    }
    
}
