package com.example.demo.service;

import java.util.Calendar;

import com.example.demo.data.Member;
import com.example.demo.data.repository.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sangmok.util.jsgrid.JsGridPageRequest;
import sangmok.util.jsgrid.JsGridRequest;
import sangmok.util.jsgrid.JsGridResponse;
import sangmok.util.jsgrid.JsGridSpecificationFactory;

@Service
public class MemberService implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    public Member getMemberByUsername(String username) {
        logger.debug("parameter: username is {}", username);
        return memberRepository.findOneByUsername(username);
    }

    public Member createMember(Member e1) {
        if (e1 == null) {
            logger.debug("entity is must not null.");
            return null;
        }
        return memberRepository.saveAndFlush(e1);
    }

    public Member createMember(String username, String password) {
        if (username == null) {
            logger.debug("username is must not null.");
            return null;
        }
        // create Member object
        Member e1 = new Member();
        e1.setUsername(username);
        e1.setPassword(encode(password));
        e1.setEnabled(false);
        return createMember(e1);
    }

    public Member modifyMember(Member e1) {
        logger.info("param e1: {}", e1);
        if (e1 == null || e1.getId() == null) {
            logger.debug("entity is must not null.");
            return null;
        }
        Member read1 = this.getMember(e1.getId());
        if (read1 == null) {
            logger.debug("not found member: {}", e1);
            return null;
        }
        return memberRepository.saveAndFlush(copyForUpdate(read1, e1));
    }

    public void removeMemberByUsername(String username) {
        if (username == null) {
            logger.debug("username is must not null.");
            return;
        }
        Member reade1 = memberRepository.findOneByUsername(username);
        if (reade1 == null) {
            logger.debug("not found by {}.", username);
            return;
        }
        memberRepository.delete(reade1);
    }

    private String encode(String raw) {
        if (passwordEncoder == null)
            passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(raw);
    }

    public Member getMember(Integer id) {
        logger.debug("id: {}", id);
        return memberRepository.getOne(id);
    }

    public void removeMember(Integer id) {
        memberRepository.deleteById(id);
    }

    public Boolean changePassword(String username, String password) {
        if (username == null || username.isEmpty())
            return false;
        if (password == null || password.isEmpty())
            return false;
        Member read1 = this.getMemberByUsername(username);
        if (read1 == null)
            return null;
        read1.setPassword(password);
        memberRepository.save(read1);
        return true;
    }

    public JsGridResponse<Member> getMembersForJsGrid(JsGridRequest jsr, Member filter) {
        logger.debug("jsGrid: request is {}, filter is {}", jsr, filter);

        if (jsr == null) {
            jsr = new JsGridRequest();
            jsr.setPageIndex(1);
            jsr.setPageSize(13);
        }

        Specification<Member> specs = Specification.where(null);
        // if (filter != null) {
        // if (filter.getId() != null && !filter.getId().equals(0)) {
        // Specification<Member> spec1 =
        // Specification.where(MemberSpecs.equalId(filter.getId()));
        // specs = Specification.where(specs).and(spec1);
        // }
        // if (filter.getUsername() != null && !filter.getUsername().isEmpty()) {
        // Specification<Member> spec1 =
        // Specification.where(MemberSpecs.likeUsername(filter.getUsername()));
        // specs = Specification.where(specs).and(spec1);
        // }
        // if (filter.getEnabled() != null) {
        // Specification<Member> spec1 =
        // Specification.where(MemberSpecs.equalEnabled(filter.getEnabled()));
        // specs = Specification.where(specs).and(spec1);
        // }
        // }

        Specification<Member> specs4 = JsGridSpecificationFactory.toSpecification(filter);
        specs = Specification.where(specs).and(specs4);

        JsGridPageRequest pageable = new JsGridPageRequest(jsr.getPageIndex() - 1, jsr.getPageSize(), jsr.getSort());
        Page<Member> page = memberRepository.findAll(specs, pageable);
        // jsgrid response
        JsGridResponse<Member> jtr = new JsGridResponse<Member>();
        jtr.setData(page.getContent());
        jtr.setItemsCount(page.getTotalElements());
        return jtr;
    }

    public static Member copyForUpdate(Member t, Member s) {
        // t.setUsername(s.getUsername());
        // t.setPassword(s.getPassword());
        t.setType(s.getType());
        t.setName(s.getName());
        t.setEmail(s.getEmail());
        if (s.getEnabled() != null)
            t.setEnabled(s.getEnabled());
        t.setExpiresOn(s.getExpiresOn());
        // t.setLastAccessedOn(s.getLastAccessedOn());
        return t;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (memberRepository.count() > 0)
            return;
        for (int i = 1; i <= 300; i++) {
            Member e1 = new Member();
            e1.setEmail(String.format("user%d@xxx.com", i));
            e1.setExpiresOn(Calendar.getInstance().getTime());
            e1.setName(String.format("user%d", i));
            e1.setPassword(encode("password"));
            e1.setUsername(String.format("user%d", i));
            e1.setEnabled(false);
            createMember(e1);
        }
        logger.info("add sample users to db.");

        Member admin = createMember("admin", "password");
        // admin.setType(MemberType.ADMIN);
        admin.setEnabled(true);
        memberRepository.save(admin);
        logger.info("add admin user to db.");
    }

}