package com.demo.daangn.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/test1")
    public String test1() {
        return "component/test1";
    }

    @GetMapping("/test2")
    public String test2() {
        return "component/test2";
    }

    @GetMapping("/test3")
    public String test3() {
        return "component/test3";
    }

    @GetMapping("/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}
