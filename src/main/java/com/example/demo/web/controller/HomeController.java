package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping
    public String home() {
        return "home";
    }

    @RequestMapping(path = "app")
    public String app_home() {
        return "home";
    }

    @RequestMapping(path = "app/login")
    public String loginHtml() {
        return "login";
    }

}