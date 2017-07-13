package com.okta.examples.oktaspringbootoauthexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/secure")
    public String secure(Model model, Principal user) {
        model.addAttribute("user", user);
        return "secure";
    }
}
