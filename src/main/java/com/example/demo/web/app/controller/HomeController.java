package com.example.demo.web.app.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "app")
@Controller
public class HomeController {

	private Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public void login_html(Locale locale) {
		logger.info("locale is {}", locale);
	}

	@RequestMapping(value = "dashboard.html")
	public void dashboard_html(Locale locale, Model model) {
		logger.info("locale: {}", locale);
	}

	@RequestMapping(value = "mypage.html")
	public void mypage_html(Locale locale, Model model) {
		logger.info("locale: {}", locale);
	}

	@RequestMapping(value = "about.html")
	public void about_html(Locale locale, Model model) {
		logger.info("locale: {}", locale);
	}

}