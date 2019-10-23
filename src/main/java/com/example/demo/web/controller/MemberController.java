/*
 * File: UserController.java
 * Project: demo
 * File Created: Monday, 21st October 2019 12:04:27 pm
 * Author: sangmok (appsoga@gmail.com)
 * -----
 * Copyright 2019 - 2019 APPSOGA Inc.
 */
package com.example.demo.web.controller;

import com.example.demo.data.Member;
import com.example.demo.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(path = "member")
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(path = "sign-in")
    public String loginHtml() {
        return "sign-in";
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getMemberByUsername(@RequestParam String username) {
        if (username == null)
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        Member e1 = memberService.getMemberByUsername(username);
        if (e1 == null)
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Member>(e1, HttpStatus.OK);
    }

}