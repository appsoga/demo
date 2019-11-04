package com.example.demo.web.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping
@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * '/' 호출시 응답, 웹어플 기본경로로 다시 보낸다.
     * 
     * @param model
     * @return
     */
    @RequestMapping
    public String home(Locale locale, Model model) {
        return "redirect:app/dashboard";
    }

    @RequestMapping(value = "app/dashboard")
    public String app_dashboard_html() {
        return "dashboard";
    }

    @RequestMapping(value = "app/login", method = RequestMethod.GET)
    public String app_login_html(Locale locale) {
        logger.info("locale is {}", locale);
        return "login";
    }

    @RequestMapping(value = "layout/default")
    public void app_default_html() {

    }

}