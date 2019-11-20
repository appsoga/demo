/*
 * File: UserController.java
 * Project: demo
 * File Created: Monday, 21st October 2019 12:04:27 pm
 * Author: sangmok (appsoga@gmail.com)
 * -----
 * Copyright 2019 - 2019 APPSOGA Inc.
 */
package com.example.demo.web.api.controller;

import java.util.Date;

import com.example.demo.data.Member;
import com.example.demo.data.MemberGroup;
import com.example.demo.service.MemberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

import sangmok.util.jsgrid.JsGridRequest;
import sangmok.util.jsgrid.JsGridResponse;

@RestController
@RequestMapping(path = "api/members")
public class ApiMemberController {

	@lombok.Data
	public static class XXRequest {
		private Integer pageIndex = 0;
		private Integer pageSize = 10;
		private String sortField;
		private String sortOrder;

		private Integer id;
		private String name;
		private String group;
		private String username;
		private String email;
		private Date expiresOn;
		private Date lastAccessedOn;
		private Boolean enabled;
	}

	private static Logger logger = LoggerFactory.getLogger(ApiMemberController.class);

	@Autowired
	private MemberService memberService;

	@PostMapping(path = "jsgrid.json")
	public ResponseEntity<?> jsgrid(@RequestBody(required = false) XXRequest sr, UriComponentsBuilder ucBuilder) {

		logger.info("sr is {}", sr);
		JsGridRequest jsr = new JsGridRequest();
		BeanUtils.copyProperties(sr, jsr);

		Member filter = new Member();
		filter.setGroup(MemberGroup.of(sr.getGroup())); // 기본속성이 아니라 복사가 안됨. 그래서 추가
		BeanUtils.copyProperties(sr, filter);

		JsGridResponse<Member> jtr = memberService.getMembersForJsGrid(jsr, filter);
		return new ResponseEntity<JsGridResponse<Member>>(jtr, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Member e1, UriComponentsBuilder ucBuilder) {
		logger.info("create member: {}", e1);
		try {
			if (e1.getUsername() == null || e1.getUsername().isEmpty()) {
				return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
			}
			e1 = memberService.createMember(e1);
			return new ResponseEntity<Member>(e1, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(
					"{timestamp: '2019-11-14T08:33:09.874+0000', status: 34, error: 'bad request', message: 'duplicated user'}",
					HttpStatus.OK);
		}
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> read(@PathVariable(name = "id") Integer id) {
		if (id == null)
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		logger.debug("request id: {}", id);
		Member e1 = memberService.getMember(id);
		if (e1 == null)
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Member>(e1, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Member e1, UriComponentsBuilder ucBuilder) {
		logger.info("update member: {}", e1);
		if (e1.getId() == null || e1.getUsername() == null)
			return new ResponseEntity<Member>(e1, HttpStatus.BAD_REQUEST);
		e1 = memberService.modifyMember(e1);
		if (e1 == null)
			return new ResponseEntity<Member>(e1, HttpStatus.NOT_FOUND);
		return new ResponseEntity<Member>(e1, HttpStatus.OK);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
		if (id == null)
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		memberService.removeMember(id);
		logger.debug("deleted id: {}", id);
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

}