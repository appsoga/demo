package com.example.demo.service;

import java.util.Calendar;

import com.example.demo.data.Member;
import com.example.demo.repository.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return createMember(e1);
    }

    public Member modifyMember(Member e1) {
        if (e1 == null) {
            logger.debug("entity is must not null.");
            return null;
        }
        logger.info("param e1: {}", e1);
        Member reade1 = getMemberByUsername(e1.getUsername());
        if (reade1 == null) {
            logger.debug("not found member: {}", e1);
            return null;
        }
        if (e1.getName() != null)
            reade1.setName(e1.getName());
        if (e1.getEmail() != null)
            reade1.setEmail(e1.getEmail());
        return memberRepository.saveAndFlush(reade1);
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
            createMember(e1);
        }
        createMember("admin", "password");
        logger.info("add user to db.");
    }

}