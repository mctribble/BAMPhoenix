package com.bam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/*
 * 
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		//.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	 public void configure(WebSecurity web) throws Exception {
	  web.ignoring().antMatchers("/index.html", "/static/**", "/");
	 }
	 
	@Override
	 protected void configure(HttpSecurity http) throws Exception {
	  http
	   .headers().disable()
	   .csrf().disable()
	   .authorizeRequests()
	    .anyRequest().authenticated()
	    .and()
	   .formLogin()
	    .loginProcessingUrl("/authenticate")
	    .usernameParameter("username")
	    .passwordParameter("password")
	    .permitAll()
	    .and()
	   .logout()
	    .logoutUrl("/logout")
	    .deleteCookies("JSESSIONID")
	    .permitAll()
	    .and();
		
	 }
}