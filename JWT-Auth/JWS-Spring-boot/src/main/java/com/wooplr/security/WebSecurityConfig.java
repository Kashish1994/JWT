package com.wooplr.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wooplr.security.jwt.JWTAuthenticationFilter;
import com.wooplr.security.jwt.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity request) throws Exception {
		// disable caches
		request.headers().cacheControl();

		request.csrf().disable().authorizeRequests().antMatchers("/").permitAll()
				.antMatchers(HttpMethod.POST, "/public/login").permitAll()
				.antMatchers(HttpMethod.POST, "/internal/user/create").access("hasRole('ADMIN')")
				.antMatchers(HttpMethod.GET, "/public/user").access("hasRole('USER')")
				.antMatchers(HttpMethod.DELETE, "/internal/user/delete/{id}").access("hasRole('ADMIN')")
				.antMatchers(HttpMethod.PUT, "/internal/user/modify").access("hasRole('ADMIN')").anyRequest()
				.authenticated().and()

				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN", "USER", "DUMMY");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");

	}
}
