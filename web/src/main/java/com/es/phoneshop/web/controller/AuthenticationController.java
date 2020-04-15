package com.es.phoneshop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "auth/login";
    }
}
