package com.example.demo.web.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class RootController {

	private Logger logger = LoggerFactory.getLogger(RootController.class);

	@RequestMapping
	public String home(Locale locale, Model model) {
		logger.info("locale: {}", locale.getLanguage());
		return "redirect:app/dashboard.html";
	}

}