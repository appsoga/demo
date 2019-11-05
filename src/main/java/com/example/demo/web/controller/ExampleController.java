package com.example.demo.web.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.data.Member;
import com.example.demo.repository.MemberRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "example")
public class ExampleController {

    private static Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    private MemberRepository memberRepository;

    /**
     * datatable의 예제
     */
    @RequestMapping(value = "datatable")
    public void app_datatable_html() {
    }

    @RequestMapping(value = "jtable")
    public void app_jtable_html() {
    }

    @RequestMapping(value = "chartjs")
    public void app_chartjs_html() {
    }

    @RequestMapping(value = "ag-grid")
    public void app_ag_grid_html() {
    }

    @RequestMapping(value = "ui-grid")
    public void app_ui_grid_html() {
    }

    // http://localhost:8080/example/jtable-list?jtStartIndex=0&jtPageSize=10&jtSorting=Name%20ASC
    @lombok.Data
    public class JTableRequest {
        private Integer jtStartIndex = 0;
        private Integer jtPageSize = 10;
        private String jtSorting;

        public Sort getSort() {
            if (jtSorting == null)
                return Sort.unsorted();
            if (jtSorting.length() < 4)
                return Sort.unsorted();

            String[] token = jtSorting.split(" ");
            if (token.length < 2)
                return Sort.unsorted();

            String property = token[0];
            Direction direction = (token[1].equalsIgnoreCase("ASC")) ? Direction.ASC : Direction.DESC;
            // return Sort.
            return Sort.by(direction, property);
        }
    }

    private static class JTablesPageRequest extends org.springframework.data.domain.PageRequest {
        private static final long serialVersionUID = 1L;

        public JTablesPageRequest(int page, int size, org.springframework.data.domain.Sort sort) {
            super(page, size, sort);
        }
    }

    @lombok.Data
    private static class JTablePageResponse<T> {
        @JsonProperty("Result")
        private String result = "OK"; // "ERROR"
        @JsonProperty("Message")
        private String message;
        @JsonProperty("Records")
        private List<T> records;
        @JsonProperty("Record")
        private T record;
    }

    @RequestMapping(value = "jtable-list")
    public @ResponseBody JTablePageResponse<Member> jtable_list(@ModelAttribute JTableRequest jr) {
        logger.info("jtable request is {}", jr);
        JTablesPageRequest pageable = new JTablesPageRequest(jr.getJtStartIndex(), jr.getJtPageSize(), jr.getSort());
        Page<Member> page = memberRepository.findAll(pageable);
        // jtable response
        JTablePageResponse<Member> jtr = new JTablePageResponse<Member>();
        jtr.setResult("OK");
        jtr.setRecords(page.getContent());
        return jtr;
    }

    @RequestMapping(value = "jtable-create")
    public @ResponseBody JTablePageResponse<Member> jtable_create(@ModelAttribute Member member) {
        Member er1 = memberRepository.save(member);
        JTablePageResponse<Member> jtr = new JTablePageResponse<Member>();
        jtr.setResult("OK");
        jtr.setRecord(er1);
        return jtr;
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
        JTablePageResponse<Member> jtr = new JTablePageResponse<Member>();
        jtr.setResult("OK");
        jtr.setRecord(er1);
        return jtr;
    }

    public static class EntityUtils {

        public static Member copyForUpdate(Member t, Member s) {
            t.setUsername(s.getUsername());
            // t.setPassword(s.getPassword());
            t.setName(s.getName());
            t.setEmail(s.getEmail());
            t.setExpiresOn(s.getExpiresOn());
            // t.setLastAccessedOn(s.getLastAccessedOn());
            return t;
        }

    }

}