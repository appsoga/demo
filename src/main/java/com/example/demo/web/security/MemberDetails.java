package com.example.demo.web.security;

import java.util.Collection;

import com.example.demo.data.Member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@lombok.Data
@lombok.EqualsAndHashCode(of = { "id" })
public class MemberDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean enabled;
	private Collection<GrantedAuthority> authorities;

	/*
	 * custom fields.
	 */
	private Integer id;
	private String name;
	private Member member;
	private Integer agencyId;

	public MemberDetails(Integer id, String name, String username, String password, Boolean accountNonExpired,
			Boolean accountNonLocked, Boolean enabled, Collection<GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.enabled = enabled;
		this.authorities = authorities;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.getUsername()).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.isEnabled()).append("; ");
		sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
		sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired()).append("; ");
		sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");

		if (!getAuthorities().isEmpty()) {
			sb.append("Granted Authorities: ");

			boolean first = true;
			for (GrantedAuthority auth : getAuthorities()) {
				if (!first) {
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

}
