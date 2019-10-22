package com.example.demo.service;

import com.example.demo.data.Member;
import com.example.demo.repository.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(MemberService.class);
    // private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private MemberRepository memberRepository;

    public Member findMemberByUsername(String username) {
        logger.debug("parameter: username is {}", username);
        return memberRepository.findOneByUsername(username);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (memberRepository.count() > 0)
            return;

        Member e1 = new Member();
        e1.setUsername("admin");
        e1.setPassword("password");
        e1 = memberRepository.save(e1);

        Member e2 = new Member();
        e2.setUsername("sangmok");
        e2.setPassword("password");
        e2 = memberRepository.save(e2);

        logger.info("add user to db.");
    }

}