package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping
@Controller
public class HomeController {

    @RequestMapping
    public String home(Model model) {
        return "redirect:app/dashboard";
    }

    @RequestMapping(value = "app/dashboard")
    public String app_dashboard() {
        return "home";
    }

    @RequestMapping(value = "app/login", method = RequestMethod.GET)
    public String loginHtml() {
        return "login";
    }

}