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

    @RequestMapping(value = "ag-grid")
    public void app_ag_grid_html() {
    }

    @RequestMapping(value = "ui-grid")
    public void app_ui_grid_html() {
    }

}