package com.demo.daangn.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChatViewController {

    // final 변수 선언
    private final String BASE_URI = "/chatRooms";

    /**
     * 채팅방 목록 페이지
     * @param req
     * @param model
     * @return
     */
    @GetMapping("/chatRooms")
    public String viewChatRooms(HttpServletRequest req, Model model) {
        model.addAttribute("navUri", BASE_URI);
        return "pages/chat/chatRooms";
    }

    /**
     * 채팅방 페이지
     * @param req
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/chatRooms/{id}")
    public String viewChatRoom(HttpServletRequest req, Model model,
        @PathVariable("id") Long id
    ) {
        model.addAttribute("navUri", BASE_URI);
        return "pages/chat/chatRoom";
    }

}
