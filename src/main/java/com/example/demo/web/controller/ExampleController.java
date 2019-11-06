package com.example.demo.web.controller;

import java.util.List;
import java.util.Optional;

import com.example.demo.data.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.web.controller.MemberController.MemberSpecs;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
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

    @RequestMapping(value = "chartjs")
    public void app_chartjs_html() {
    }

    @RequestMapping(value = "jqgrid")
    public void app_jqgrid_html() {
    }

    @RequestMapping(value = "jqgrid-list")
    public @ResponseBody JqGridResponse<Member> jqgrid_list(@ModelAttribute Member filter,
            @ModelAttribute JqGridRequest jr) {
        logger.info("jtable request is {}, filter: {}", jr, filter);
        JTablesPageRequest pageable = new JTablesPageRequest(jr.getPage() - 1, jr.getRows(), jr.getSort());
        Page<Member> page = memberRepository.findAll(pageable);
        // jtable response
        JqGridResponse<Member> jtr = new JqGridResponse<Member>();
        jtr.setData(page.getContent());
        jtr.setTotalRecords(page.getTotalElements());
        jtr.setTotalPageSize(page.getTotalPages());
        jtr.setCurrentPage(page.getNumber() + 1);
        return jtr;
    }

    @lombok.Data
    public static class JqGridRequest {
        private Integer q;
        @JsonProperty("page")
        private Integer page = 1;
        @JsonProperty("rows")
        private Integer rows = 10;
        @JsonProperty("sidx")
        private String sidx;
        @JsonProperty("sord")
        private String sord;

        @JsonProperty("_search")
        private Boolean search;

        private Long nd;

        public Sort getSort() {
            if (sidx == null || sidx.isEmpty())
                return Sort.unsorted();
            if (sord == null || sord.isEmpty())
                return Sort.unsorted();

            Direction direction = (sord.equalsIgnoreCase("ASC")) ? Direction.ASC : Direction.DESC;
            // return Sort.
            return Sort.by(direction, sidx);
        }
    }

    // {"page":1,"total":1,"records":1,"rows":[{"id":1,"cell":["johnsmith",1,"John","Smith",1]}]}

    @lombok.Data
    public static class JqGridResponse<T> {
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @JsonProperty("records")
        private Long totalRecords;
        @JsonProperty("page")
        private Integer currentPage;
        @JsonProperty("total")
        private Integer totalPageSize;
        @JsonProperty("rows")
        private List<T> data;
    }

    // #####################################
    // jTable example
    // #####################################

    @RequestMapping(value = "jtable")
    public void app_jtable_html() {
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

    // #####################################
    // jTable example
    // #####################################

    @RequestMapping(value = "jsgrid")
    public void app_jgrid_html() {
    }

    @lombok.Data
    public static class JsGridRequest {
        private Integer pageIndex = 0;
        private Integer pageSize = 10;
        private String sortField;
        private String sortOrder;

        public Sort getSort() {
            if (sortField == null)
                return Sort.unsorted();
            if (sortOrder == null)
                return Sort.unsorted();

            Direction direction = (sortOrder.equalsIgnoreCase("ASC")) ? Direction.ASC : Direction.DESC;
            // return Sort.
            return Sort.by(direction, sortField);
        }
    }

    @lombok.Data
    public static class JsGridResponse<T> {
        private List<T> data;
        private Long itemsCount;
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
            Specification<Member> spec1 = Specification.where(MemberSpecs.equalUsername(filter.getUsername()));
            specs = Specification.where(specs).and(spec1);
        }

        JTablesPageRequest pageable = new JTablesPageRequest(jsr.getPageIndex() - 1, jsr.getPageSize(), jsr.getSort());
        Page<Member> page = memberRepository.findAll(specs, pageable);
        // jsgrid response
        JsGridResponse<Member> jtr = new JsGridResponse<Member>();
        jtr.setData(page.getContent());
        jtr.setItemsCount(page.getTotalElements());
        return jtr;
    }

}