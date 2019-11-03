package com.example.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "example")
public class ExampleController {

    /**
     * datatable의 예제
     */
    @RequestMapping(value = "datatable")
    public void app_datatable_html() {
    }

    @RequestMapping(value = "chartjs")
    public void app_chartjs_html() {
    }

}