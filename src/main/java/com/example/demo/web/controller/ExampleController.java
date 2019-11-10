package com.example.demo.web.controller;

import com.example.demo.data.Member;
import com.example.demo.data.repository.MemberRepository;
import com.example.demo.service.MemberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sangmok.util.jqgrid.JqGridPageRequest;
import sangmok.util.jqgrid.JqGridRequest;
import sangmok.util.jqgrid.JqGridResponse;
import sangmok.util.jsgrid.JsGridRequest;
import sangmok.util.jsgrid.JsGridResponse;
import sangmok.util.jtable.JTablePageResponse;
import sangmok.util.jtable.JTableRequest;
import sangmok.util.jtable.JTablesPageRequest;

@Controller
@RequestMapping(value = "example")
public class ExampleController {

	private static Logger logger = LoggerFactory.getLogger(ExampleController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	/**
	 * datatable의 예제
	 */
	@RequestMapping(value = "datatable.html")
	public void app_datatable_html() {
	}

	@RequestMapping(value = "chartjs.html")
	public void app_chartjs_html() {
	}

	@RequestMapping(value = "jqgrid.html")
	public void app_jqgrid_html() {
	}

	@RequestMapping(value = "jqgrid2.html")
	public void app_jqgrid2_html() {
	}

	@RequestMapping(value = "jqgrid-list")
	public @ResponseBody JqGridResponse<Member> jqgrid_list(@ModelAttribute Member filter,
			@ModelAttribute JqGridRequest jr) {
		logger.info("jtable request is {}, filter: {}", jr, filter);
		JqGridPageRequest pageable = new JqGridPageRequest(jr.getPage() - 1, jr.getRows(), jr.getSort());
		Page<Member> page = memberRepository.findAll(pageable);
		return new JqGridResponse<Member>(page);
	}

	/** jsGrid를 위한 영역 */

	@RequestMapping(value = "jsgrid.html")
	public void app_jsgrid_html() {
	}

	@RequestMapping(value = "jsgrid.json")
	public @ResponseBody JsGridResponse<Member> jsgrid_list(@ModelAttribute Member filter,
			@ModelAttribute JsGridRequest jsr) {
		logger.info("jtable request is {}, filter is {}", jsr, filter);
		return memberService.getMembersForJsGrid(jsr, filter);
	}

	/**
	 * tTable을 위한 영역
	 */
	@RequestMapping(value = "jtable.html")
	public void app_jtable_html() {
	}

	@RequestMapping(value = "jtable-list")
	public @ResponseBody JTablePageResponse<Member> jtable_list(@ModelAttribute JTableRequest jr) {
		logger.info("jtable request is {}", jr);
		JTablesPageRequest pageable = new JTablesPageRequest(jr.getPage(), jr.getJtPageSize(), jr.getSort());
		Page<Member> page = memberRepository.findAll(pageable);
		// jtable response
		return new JTablePageResponse<Member>(page);
	}

	@RequestMapping(value = "jtable-create")
	public @ResponseBody JTablePageResponse<Member> jtable_create(@ModelAttribute Member member) {
		return new JTablePageResponse<Member>(memberService.createMember(member));
	}

	@RequestMapping(value = "jtable-delete")
	public @ResponseBody JTablePageResponse<Member> jtable_delete(@ModelAttribute Member member) {
		memberService.removeMember(member.getId());
		JTablePageResponse<Member> jtr = new JTablePageResponse<Member>();
		jtr.setResult("OK");
		return jtr;
	}

	@RequestMapping(value = "jtable-update")
	public @ResponseBody JTablePageResponse<Member> jtable_update(@ModelAttribute Member member) {
		return new JTablePageResponse<Member>(memberService.modifyMember(member));
	}

}