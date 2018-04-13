package com.wooplr.security.jwt;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticatedUser implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4329121431640068926L;

	private boolean authenticated = true;

	private Collection<? extends GrantedAuthority> authority;

	private String name;

	public AuthenticatedUser(String name) {
		super();
		this.name = name;

	}

	public AuthenticatedUser(String name, Collection<? extends GrantedAuthority> authority) {
		super();
		this.name = name;
		this.authority = authority;

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}
}
