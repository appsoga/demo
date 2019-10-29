package com.example.demo.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static final MemberDetails getPrincipal() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		Object obj = authentication == null ? null : authentication.getPrincipal();
		if (obj instanceof MemberDetails)
			return (MemberDetails) obj;
		return null;
	}

	public static final Integer getMemberId() {
		MemberDetails uDetails = getPrincipal();
		return uDetails == null ? 0 : uDetails.getId();
	}

}
