package com.example.demo.web.app.controller;

import com.example.demo.service.EncodedJsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "app/js")
public class EncodedJsController {

    @Autowired
    private EncodedJsService jsService;

    @RequestMapping(value = "env.js", produces = "text/javascript")
    public @ResponseBody String env_js() {
        return jsService.encodedJs();
    }

}