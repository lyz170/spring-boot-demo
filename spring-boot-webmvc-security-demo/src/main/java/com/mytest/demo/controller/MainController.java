package com.mytest.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/a1/index")
    public String a1Index() {
        return "a1/index";
    }

    @RequestMapping("/a2/index")
    public String a2Index() {
        return "a2/index";
    }

    @RequestMapping("/a3/index")
    public String a3Index() {
        return "a3/index";
    }

    @RequestMapping("/b1/index")
    public String b1Index() {
        return "b1/index";
    }

    @RequestMapping("/b2/index")
    public String b2Index() {
        return "b2/index";
    }

    @RequestMapping("/c1/index")
    public String c1Index() {
        return "c1/index";
    }

    @RequestMapping("/c2/index")
    public String c2Index() {
        return "c2/index";
    }

    @RequestMapping("/c3/index")
    public String c3Index() {
        return "c3/index";
    }

    @RequestMapping("/c4/index")
    public String c4Index() {
        return "c4/index";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/c5/test")
    public String c5test() {
        return "c5/test";
    }
}