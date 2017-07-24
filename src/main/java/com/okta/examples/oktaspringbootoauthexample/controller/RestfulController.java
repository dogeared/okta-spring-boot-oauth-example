package com.okta.examples.oktaspringbootoauthexample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RestfulController {

    @RequestMapping("/secure-api")
    public @ResponseBody Principal secureApi(Principal principal) {
        return principal;
    }
}
