package com.wooplr.security.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class ClaimBody {
	private Collection<? extends GrantedAuthority> authority;

	public Collection<? extends GrantedAuthority> getAuthority() {
		return authority;
	}

	public void setAuthority(Collection<? extends GrantedAuthority> authority) {
		this.authority = authority;
	}

}
