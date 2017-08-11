package com.bam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/*
 * 
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Get user from the database, via Hibernate
	@Autowired
	@Qualifier("userDetailsService")

	UserDetailsService userDetailsService;//LoadUsername() will load the User Record from DB 
										  //Past back a Spring Security 
	// LoadUsername() will load the User record from the DB
	// Pass back a Spring Security User Object NOT BAMUser object

	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler restAuthenticationFailureHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		// .passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
<<<<<<< HEAD
	 public void configure(WebSecurity web) throws Exception {

		// Ignore certain URLs.
	  web.ignoring().antMatchers("/index.html", "/static/**", "/");
	 }
=======
	public void configure(WebSecurity web) throws Exception {
		// Ignore certain URLs

		web.ignoring().antMatchers("/index.html", "/static/**", "/");
	}
>>>>>>> 7ca5951d4dddce48b1e438bf3c9746b8964ab3db

	@Override

	 protected void configure(HttpSecurity http) throws Exception {
	  http
	   .headers().disable()
	   .csrf().disable()//not suppose to disable this in production
	   .authorizeRequests()
	   	//.antMatchers("/Batches/**").hasRole("2")
	   	.antMatchers("/Users/Register.do").permitAll()
	    .anyRequest().authenticated()
	    .and()
	    .formLogin()
	   	.loginPage("/")
	    .loginProcessingUrl("/authenticate")
	    .successHandler(restAuthenticationSuccessHandler)
	    .failureHandler(restAuthenticationFailureHandler)
	    .usernameParameter("username")
	    .passwordParameter("password")
	    .permitAll()
	    .and()
	    .logout()
	    .logoutUrl("/logout")
	    //.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
	    .deleteCookies("JSESSIONID")
	    .permitAll()
	    .and();
		
	 }
}
