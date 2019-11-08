package com.example.demo.web.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.data.Member;
import com.example.demo.data.repository.MemberRepository;
import com.example.demo.data.specs.MemberSpecs;

import sangmok.util.jqgrid.JqGridPageRequest;
import sangmok.util.jqgrid.JqGridRequest;
import sangmok.util.jqgrid.JqGridResponse;
import sangmok.util.jsgrid.JsGridPageRequest;
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

	@RequestMapping(value = "jsgrid.html")
	public void app_jsgrid_html() {
	}

	@RequestMapping(value = "jsgrid-list")
	public @ResponseBody JsGridResponse<Member> jsgrid_list(@ModelAttribute Member filter,
			@ModelAttribute JsGridRequest jsr) {
		logger.info("jtable request is {}, filter is {}", jsr, filter);

		Specification<Member> specs = Specification.where(null);
		if (filter.getId() != null && !filter.getId().equals(0)) {
			Specification<Member> spec1 = Specification.where(MemberSpecs.equalId(filter.getId()));
			specs = Specification.where(specs).and(spec1);
		}
		if (!filter.getUsername().isEmpty()) {
			Specification<Member> spec1 = Specification.where(MemberSpecs.likeUsername(filter.getUsername()));
			specs = Specification.where(specs).and(spec1);
		}

		if (filter.getEnabled() != null) {
			Specification<Member> spec1 = Specification.where(MemberSpecs.equalEnabled(filter.getEnabled()));
			specs = Specification.where(specs).and(spec1);
		}

		JsGridPageRequest pageable = new JsGridPageRequest(jsr.getPageIndex() - 1, jsr.getPageSize(), jsr.getSort());
		Page<Member> page = memberRepository.findAll(specs, pageable);
		// jsgrid response
		JsGridResponse<Member> jtr = new JsGridResponse<Member>();
		jtr.setData(page.getContent());
		jtr.setItemsCount(page.getTotalElements());
		return jtr;
	}

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
		Member er1 = memberRepository.save(member);
		return new JTablePageResponse<Member>(er1);
	}

	@RequestMapping(value = "jtable-delete")
	public @ResponseBody JTablePageResponse<Member> jtable_delete(@ModelAttribute Member member) {
		memberRepository.delete(member);
		JTablePageResponse<Member> jtr = new JTablePageResponse<Member>();
		jtr.setResult("OK");
		return jtr;
	}

	@RequestMapping(value = "jtable-update")
	public @ResponseBody JTablePageResponse<Member> jtable_update(@ModelAttribute Member member) {
		Integer id = member.getId();
		Optional<Member> o1 = memberRepository.findById(id);
		Member e1 = EntityUtils.copyForUpdate(o1.get(), member);
		Member er1 = memberRepository.save(e1);
		return new JTablePageResponse<Member>(er1);
	}

	public static class EntityUtils {

		public static Member copyForUpdate(Member t, Member s) {
			// t.setUsername(s.getUsername());
			// t.setPassword(s.getPassword());
			t.setName(s.getName());
			t.setEmail(s.getEmail());
			t.setEnabled(s.getEnabled());
			t.setExpiresOn(s.getExpiresOn());
			// t.setLastAccessedOn(s.getLastAccessedOn());
			return t;
		}

	}

}