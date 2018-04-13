package com.wooplr.security.jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
	private String secret = "ThisIsASecret";
	private String tokenPrefix = "Bearer";
	private String headerString = "Authorization";

	public void addAuthentication(HttpServletResponse response, String username, Authentication authResult) {

		Collection<?> authority = authResult.getAuthorities();

		Object o = authResult.getDetails();
		Claims claims = Jwts.claims().setSubject(authResult.getName());
		claims.put("principal", authResult.getPrincipal());

		String JWT = Jwts.builder().setSubject(username).setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		response.addHeader(headerString, tokenPrefix + " " + JWT);
	}

	public AuthenticatedUser getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(headerString);
		if (token != null) {
			// parse the token.

			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			String username = claims.getBody().getSubject();

			StringBuffer sb = new StringBuffer("");

			LinkedHashMap nn = (LinkedHashMap) claims.getBody().get("principal");
			ArrayList auths = (ArrayList) nn.get("authorities");

			for (int i = 0; i < auths.size(); i++) {
				String rolesAsString = auths.get(i).toString();
				String roleArray[] = rolesAsString.split("=");
				String role = roleArray[1];
				String roleToPut = role.replaceFirst("}", "");
				if (auths.size() - 1 == i) {
					sb.append(roleToPut);
				} else {
					sb.append(roleToPut + ",");
				}
			}

			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList(sb.toString());

			if (username != null) { // we managed to retrieve a user
				return new AuthenticatedUser(username, grantedAuthorities);
			}
		}
		return null;
	}
}
