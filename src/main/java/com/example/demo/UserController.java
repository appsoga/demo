/*
 * File: UserController.java
 * Project: demo
 * File Created: Monday, 21st October 2019 12:04:27 pm
 * Author: sangmok (appsoga@gmail.com)
 * -----
 * Copyright 2019 - 2019 APPSOGA Inc.
 */
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "user")
@Controller
public class UserController {

    @RequestMapping(path = "sign-in")
    public String loginHtml() {
        return "sign-in";
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