package com.example.demo.service;

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

    public Member addMember(String username, String password) {
        if (username == null) {
            logger.debug("username is must not null.");
            return null;
        }
        // create Member object
        Member e1 = new Member();
        e1.setUsername(username);
        e1.setPassword(encode(password));
        return memberRepository.saveAndFlush(e1);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (memberRepository.count() > 0)
            return;

        Member e1 = new Member();
        e1.setUsername("admin");
        e1.setPassword(encode("password"));
        e1 = memberRepository.save(e1);

        Member e2 = new Member();
        e2.setUsername("sangmok");
        e2.setPassword(encode("password"));
        e2 = memberRepository.save(e2);

        logger.info("add user to db.");
    }

    private String encode(String raw) {
        if (passwordEncoder == null)
            passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(raw);
    }

}