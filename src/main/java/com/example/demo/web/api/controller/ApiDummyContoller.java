package com.example.demo.web.api.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.demo.web.api.model.ApiPagingRequest;
import com.example.demo.web.api.model.ApiPagingRequest.Paging;
import com.example.demo.web.api.model.ApiPagingRequest.WebApiRequest;
import com.example.demo.web.api.model.ApiPagingResponse;
import com.example.demo.web.api.model.ApiPagingResponse.WebApiResponse;
import com.example.demo.web.api.model.ApiRequest;
import com.example.demo.web.api.model.ApiResponse;

@RestController
@RequestMapping(value = { "api/dummy/members" })
public class ApiDummyContoller {

	@lombok.Data
	public static class Member {
		private Integer id;
		private String username;
		private String password;
		private String group;
		private Date expiresOn;
		private Date lastAccessedOn;
		private Boolean locked;
		private Boolean enabled;
		private String name;
		private String email;
		private Date modifiedOn;
		private Integer worker;
	}

	private static Logger logger = LoggerFactory.getLogger(ApiDummyContoller.class);

	private Member createMember(int i) {
		Member e1 = new Member();
		e1.setId(i);
		e1.setUsername(String.format("username%d", i));
		e1.setGroup((i % 5 == 3 ? "A" : "O"));
		e1.setExpiresOn(Calendar.getInstance().getTime());
		e1.setLastAccessedOn(Calendar.getInstance().getTime());
		e1.setLocked(i % 3 == 0 ? true : false);
		e1.setName(String.format("name%d", i));
		e1.setEmail(String.format("user%d@mail.com", i));
		e1.setModifiedOn(Calendar.getInstance().getTime());
		e1.setEnabled((i % 3 == 1 ? true : false));
		e1.setWorker(1);
		return e1;
	}

	@PostMapping
	public ResponseEntity<ApiPagingResponse<?>> inspect(@RequestHeader(value = "accept") String accept,
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "content-type") String contentType, @RequestHeader(value = "tranId") String tranId,
			@RequestHeader(value = "id") String id, @RequestHeader(value = "host") String host,

			@RequestBody(required = false) ApiPagingRequest sr, UriComponentsBuilder ucBuilder) {
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
		logger.info("id: {}", id);
		logger.info("host: {}", host);
		logger.info("request: {}", sr);

		/*
		 * 가상의 결과값을 생성한다.
		 */
		List<Member> operatorList = new ArrayList<Member>();
		Integer totalCnt = 3043;

		int pageIndex = sr.getWebApi().getPaging().getPageIndex();
		int lineCnt = sr.getWebApi().getPaging().getLineCount();

		int index = ((pageIndex - 1) * lineCnt) + 1;
		int limit = pageIndex * lineCnt;
		limit = (limit < totalCnt) ? limit : totalCnt;
		for (int i = index; i <= limit; i++) {
			Member e1 = createMember(i);
			operatorList.add(e1);
		}
		//
		WebApiResponse<Member> webApi = new WebApiResponse<Member>();
		webApi.setOperatorList(operatorList);
		webApi.setTotalCnt(totalCnt);
		//
		ApiPagingResponse<Member> mr = new ApiPagingResponse<Member>();
		mr.setWebApi(webApi);

		return new ResponseEntity<ApiPagingResponse<?>>(mr, HttpStatus.OK);
	}

	@PostMapping(path = "create")
	public ResponseEntity<?> insert(@RequestHeader(value = "accept") String accept,
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "content-type") String contentType, @RequestHeader(value = "tranId") String tranId,
			@RequestHeader(value = "id") String id, @RequestHeader(value = "host") String host,

			@RequestBody ApiRequest<Member> e1, UriComponentsBuilder ucBuilder) {

		logger.info("### insert --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("id: {}", id);
		logger.info("host: {}", host);

		logger.info("request: {}", e1);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<?> selectOne(@RequestHeader(value = "accept") String accept,
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "content-type") String contentType, @RequestHeader(value = "tranId") String tranId,
			@RequestHeader(value = "id") String id, @RequestHeader(value = "host") String host,

			@PathVariable(value = "id") Integer rId) {

		logger.info("### selectOne --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("id: {}", id);
		logger.info("host: {}", host);

		logger.info("request: rid: {}", rId);

		Member e1 = createMember(rId);
		ApiResponse<Member> mr = new ApiResponse<Member>();
		mr.setWebApi(e1);
		return new ResponseEntity<ApiResponse<Member>>(mr, HttpStatus.OK);
	}

	@PutMapping(path = "{id}")
	public ResponseEntity<?> update(@RequestHeader(value = "accept") String accept,
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "content-type") String contentType, @RequestHeader(value = "tranId") String tranId,
			@RequestHeader(value = "id") String id, @RequestHeader(value = "host") String host,

			@RequestBody ApiRequest<Member> e1, UriComponentsBuilder ucBuilder) {

		logger.info("### update --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("id: {}", id);
		logger.info("host: {}", host);

		logger.info("request: {}", e1);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@RequestHeader(value = "accept") String accept,
			@RequestHeader(value = "access_token") String accessToken,
			@RequestHeader(value = "content-type") String contentType, @RequestHeader(value = "tranId") String tranId,
			@RequestHeader(value = "id") String id, @RequestHeader(value = "host") String host,

			@RequestBody ApiRequest<Member> e1, UriComponentsBuilder ucBuilder) {

		logger.info("### delete --------------");
		logger.info("content-type: {}", contentType);
		logger.info("access_token: {}", accessToken);
		logger.info("tranId: {}", tranId);
		logger.info("id: {}", id);
		logger.info("host: {}", host);

		logger.info("request: {}", e1);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
