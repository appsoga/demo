package com.example.demo.web.api.controller;

import com.example.demo.service.MemberDummyService;
import com.example.demo.service.MemberDummyService.Member;
import com.example.demo.web.api.model.ApiPagingRequest;
import com.example.demo.web.api.model.ApiPagingRequest.Paging;
import com.example.demo.web.api.model.ApiPagingRequest.WebApiRequest;
import com.example.demo.web.api.model.ApiPagingResponse;
import com.example.demo.web.api.model.ApiRequest;
import com.example.demo.web.api.model.ApiResponse;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value = { "api/dummy/members" })
public class ApiDummyContoller {

	@Autowired
	private MemberDummyService memberService;

	private static Logger logger = LoggerFactory.getLogger(ApiDummyContoller.class);

	@PostMapping
	public ResponseEntity<ApiPagingResponse<?>> inspect(@RequestHeader(value = "content-type") String contentType,
			@RequestHeader(value = "accept") String accept, @RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "tranId") String tranId, @RequestHeader(value = "id") String worker,
			@RequestHeader(value = "host") String host, @RequestBody(required = false) ApiPagingRequest sr,
			UriComponentsBuilder ucBuilder) {

		if (sr == null) {
			Paging paging = new Paging();
			WebApiRequest webApi = new WebApiRequest();
			webApi.setPaging(paging);
			sr = new ApiPagingRequest();
			sr.setWebApi(webApi);
		}

		logger.info("### inspect (paging) --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("worker: {}", worker);
		logger.info("host: {}", host);
		logger.info("request: {}", sr);

		//
		ApiPagingResponse<Member> mr = memberService.findAll(sr);
		return new ResponseEntity<ApiPagingResponse<?>>(mr, HttpStatus.OK);
	}

	@PostMapping(path = "create")
	public ResponseEntity<?> insert(@RequestHeader(value = "content-type") String contentType,
			@RequestHeader(value = "accept") String accept, @RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "tranId") String tranId, @RequestHeader(value = "id") String worker,
			@RequestHeader(value = "host") String host, @RequestBody ApiRequest<Member> sr,
			UriComponentsBuilder ucBuilder) {

		logger.info("### insert --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("worker: {}", worker);
		logger.info("host: {}", host);
		logger.info("request: {}", sr);

		Member e1 = new Member();
		BeanUtils.copyProperties(sr.getWebApi(), e1);
		//
		e1 = memberService.createMember(e1);
		//
		ApiResponse<Member> mr = new ApiResponse<Member>();
		mr.setWebApi(e1);
		return new ResponseEntity<Object>(mr, HttpStatus.OK);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> selectOne(@RequestHeader(value = "content-type") String contentType,
			@RequestHeader(value = "accept") String accept, @RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "tranId") String tranId, @RequestHeader(value = "id") String worker,
			@RequestHeader(value = "host") String host, @PathVariable(value = "id") Integer id) {

		logger.info("### selectOne --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("worker: {}", worker);
		logger.info("host: {}", host);
		logger.info("request: rid: {}", id);

		Member e1 = memberService.findMember(new Member(id));
		//
		ApiResponse<Member> mr = new ApiResponse<Member>();
		mr.setWebApi(e1);
		return new ResponseEntity<ApiResponse<Member>>(mr, HttpStatus.OK);
	}

	@PutMapping(path = "{id}")
	public ResponseEntity<?> update(@RequestHeader(value = "content-type") String contentType,
			@RequestHeader(value = "accept") String accept, @RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "tranId") String tranId, @RequestHeader(value = "id") String worker,
			@RequestHeader(value = "host") String host, @PathVariable(value = "id") Integer id,
			@RequestBody ApiRequest<Member> sr, UriComponentsBuilder ucBuilder) {

		logger.info("### update --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("worker: {}", worker);
		logger.info("host: {}", host);
		logger.info("request: {}", sr);
		//
		Member e1 = new Member();
		BeanUtils.copyProperties(sr.getWebApi(), e1);
		e1 = memberService.modifyMember(e1);
		//
		ApiResponse<Member> mr = new ApiResponse<Member>();
		mr.setWebApi(e1);
		return new ResponseEntity<ApiResponse<Member>>(mr, HttpStatus.OK);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@RequestHeader(value = "content-type") String contentType,
			@RequestHeader(value = "accept") String accept, @RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "tranId") String tranId, @RequestHeader(value = "id") String worker,
			@RequestHeader(value = "host") String host, @PathVariable(value = "id") Integer id,
			@RequestBody ApiRequest<Member> sr, UriComponentsBuilder ucBuilder) {

		logger.info("### delete --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("worker: {}", worker);
		logger.info("host: {}", host);
		logger.info("request: {}", sr);

		Member e1 = new Member();
		BeanUtils.copyProperties(sr.getWebApi(), e1);

		memberService.removeMember(id);

		ApiResponse<Member> mr = new ApiResponse<Member>();
		return new ResponseEntity<ApiResponse<Member>>(mr, HttpStatus.OK);
	}

}
