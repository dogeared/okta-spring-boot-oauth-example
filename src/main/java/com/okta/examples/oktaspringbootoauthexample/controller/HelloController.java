package com.okta.examples.oktaspringbootoauthexample.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HelloController {

    @Value("#{ @environment['okta.tenant.uri'] }")
    private String oktaTenantUri;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/secure")
    public String secure(Model model, Principal user) {
        model.addAttribute("user", user);
        model.addAttribute("oktaTenantUri", oktaTenantUri);
        return "secure";
    }
}
