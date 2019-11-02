package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "example")
@Controller
public class ExampleController {

    /**
     * datatable의 예제
     */
    @RequestMapping(value = "datatable")
    public String app_datatable_html() {
        return "datatable";
    }

}