package com.example.demo.web.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.data.Member;
import com.example.demo.service.MemberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);

	@Autowired
	private MemberService memberService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("find member by username: {}", username);

		Member member = memberService.getMemberByUsername(username);
		if (member == null) {
			logger.warn("username({}) not found.", username);
			throw new UsernameNotFoundException(username);
		}
		logger.info("found member: {}", member);

		// isAccountNonExpired
		Boolean accountNonExpired = false;
		if (member.getExpiresOn() == null)
			accountNonExpired = true;
		else if (member.getExpiresOn().compareTo(Calendar.getInstance().getTime()) > 0)
			accountNonExpired = true;
		else
			accountNonExpired = false;

		// accountNonLocked
		Boolean accountNonLocked = true;

		// enabled
		Boolean enabled = true;

		/*
		 * authorities
		 */
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		// TODO: here is authorities addition.

		/*
		 * sort...
		 */
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(authorities);
		Comparator<GrantedAuthority> c = new Comparator<GrantedAuthority>() {
			@Override
			public int compare(GrantedAuthority o1, GrantedAuthority o2) {
				return o1.getAuthority().compareTo(o2.getAuthority());
			}
		};
		Collections.sort(grantedAuthorities, c);

		/*
		 * MemberDetails for login
		 */
		MemberDetails details = new MemberDetails(member.getId(), member.getName(), member.getUsername(),
				member.getPassword(), accountNonExpired, accountNonLocked, enabled, grantedAuthorities);
		details.setMember(member);

		// return details.
		return details;
	}

}
