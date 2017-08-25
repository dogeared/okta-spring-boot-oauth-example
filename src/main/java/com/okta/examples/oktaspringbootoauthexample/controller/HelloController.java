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

    @Value("#{ @environment['okta.moreSecure.authorities'] }")
    String[] moreSecureAuthorities;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/secure")
    public String secure(Model model, Principal user) {
        model.addAttribute("user", user);
        model.addAttribute("oktaTenantUri", oktaTenantUri);
        model.addAttribute("authorities", moreSecureAuthorities);
        return "secure";
    }

    // you have to be a member of the groups defined in SpringSecurityWebAppConfig
    @RequestMapping("/more-secure")
    public String moreSecure(Model model, Principal user) {
        return secure(model, user);
    }
}
