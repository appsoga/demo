package com.example.demo.web.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = { "app/system" })
@Controller
public class SystemController {

	private static Logger logger = LoggerFactory.getLogger(SystemController.class);

	@RequestMapping(value = { "" })
	public String _system(Model model) {
		return "redirect:./members.html";
	}

	@RequestMapping(value = "members.html")
	public void members_html(Model model) {
		logger.debug("model: {}", model);

	}

}
