package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping
    public String home() {
        return "home";
    }

    @RequestMapping(path = "login")
    public String loginHtml() {
        return "login";
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam String name) {
        User o = new User();
        o.setId(1);
        o.setName(name);
        return o;
    }

}