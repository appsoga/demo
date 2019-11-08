package com.example.demo.web.app.controller;

import com.example.demo.data.Member;
import com.example.demo.data.repository.MemberRepository;
import com.example.demo.data.specs.MemberSpecs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sangmok.util.jsgrid.JsGridPageRequest;
import sangmok.util.jsgrid.JsGridRequest;
import sangmok.util.jsgrid.JsGridResponse;

@RequestMapping(path = "app/integrated")
@Controller
public class IntegratedController {

    private Logger logger = LoggerFactory.getLogger(IntegratedController.class);

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(path = "manager.html")
    public void app_manager_html(Model model) {
        logger.info("");
    }

    @RequestMapping(value = "jsgrid-list")
    public @ResponseBody JsGridResponse<Member> jsgrid_list(@ModelAttribute Member filter,
            @ModelAttribute JsGridRequest jsr) {
        logger.info("jtable request is {}, filter is {}", jsr, filter);

        Specification<Member> specs = Specification.where(null);
        if (filter != null) {
            if (!isEmpty(filter.getId())) {
                Specification<Member> spec1 = Specification.where(MemberSpecs.equalId(filter.getId()));
                specs = Specification.where(specs).and(spec1);
            }
            if (!isEmpty(filter.getUsername())) {
                Specification<Member> spec1 = Specification.where(MemberSpecs.likeUsername(filter.getUsername()));
                specs = Specification.where(specs).and(spec1);
            }
            if (!isEmpty(filter.getName())) {
                Specification<Member> spec1 = Specification.where(MemberSpecs.likeName(filter.getName()));
                specs = Specification.where(specs).and(spec1);
            }
            if (!isEmpty(filter.getEnabled())) {
                Specification<Member> spec1 = Specification.where(MemberSpecs.equalEnabled(filter.getEnabled()));
                specs = Specification.where(specs).and(spec1);
            }
        }

        JsGridPageRequest pageable = new JsGridPageRequest(jsr.getPageIndex() - 1, jsr.getPageSize(), jsr.getSort());
        Page<Member> page = memberRepository.findAll(specs, pageable);
        // jsgrid response
        JsGridResponse<Member> jtr = new JsGridResponse<Member>();
        jtr.setData(page.getContent());
        jtr.setItemsCount(page.getTotalElements());
        return jtr;
    }

    private boolean isEmpty(Object o1) {
        if (o1 == null)
            return true;
        if (o1 instanceof java.lang.String)
            return ((java.lang.String) o1).isEmpty();
        return false;
    }

}