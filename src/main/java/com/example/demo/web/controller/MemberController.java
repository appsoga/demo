package com.example.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "app/member")
@Controller
public class MemberController {

    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping(value = "list")
    public void member_list(Model model) {
        logger.debug("model: {}", model);

    }

}