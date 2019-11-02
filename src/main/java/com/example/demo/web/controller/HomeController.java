package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping
@Controller
public class HomeController {

    /**
     * '/' 호출시 응답으로 웹어플 기본경로로 다시 보낸다.
     * 
     * @param model
     * @return
     */
    @RequestMapping
    public String home(Model model) {
        return "redirect:app/dashboard";
    }

    @RequestMapping(value = "app/dashboard")
    public String app_dashboard_html() {
        return "dashboard";
    }

    @RequestMapping(value = "app/login", method = RequestMethod.GET)
    public String app_login_html() {
        return "login";
    }
}