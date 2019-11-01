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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping(value = "api/member")
@RestController
public class MemberApiController {

    private static Logger logger = LoggerFactory.getLogger(MemberApiController.class);

    @Autowired
    private MemberService memberService;

    @PostMapping(path = "create")
    // @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Member e1, UriComponentsBuilder ucBuilder) {
        logger.info("create member: {}", e1);
        e1 = memberService.createMember(e1);
        return new ResponseEntity<Member>(e1, HttpStatus.OK);
    }

    @GetMapping(path = "{username}")
    // @RequestMapping(path = "{username}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable(value = "username") String username) {
        if (username == null)
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        logger.debug("username is {}", username);
        Member e1 = memberService.getMemberByUsername(username);
        if (e1 == null)
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Member>(e1, HttpStatus.OK);
    }

    @PutMapping(value = "{username}")
    // @RequestMapping(value = "{username}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable(value = "username") String username, @RequestBody Member e1,
            UriComponentsBuilder ucBuilder) {
        logger.info("update member: {}", e1);
        if (e1 == null || !e1.getUsername().equals(username))
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        e1 = memberService.modifyMember(e1);
        return new ResponseEntity<Member>(e1, HttpStatus.OK);
    }

    @DeleteMapping(value = "{username}")
    // @RequestMapping(path = "{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(value = "username") String username) {
        if (username == null)
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        logger.debug("username is {}", username);
        memberService.removeMemberByUsername(username);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}