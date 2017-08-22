package com.bam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 
 * @author Duncan Hayward
 * Getting user from the database with
 * @Autowired
 * UserDetailsService
 *
 *LoadUsername() will load the User Record from DB 
  //Past back a Spring Security 
  // LoadUsername() will load the User record from the DB
  // Pass back a Spring Security User Object NOT BAMUser object
   * .passwordEncoder(new BCryptPasswordEncoder());
   * 
   * Don't disable csrf in production
   * 
   *  //.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler restAuthenticationFailureHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	
	}

	@Override
	 public void configure(WebSecurity web) throws Exception {

		// Ignore certain URLs.
	  web.ignoring().antMatchers("/index.html", "/static/**", "/");
	 }


	@Override
	 protected void configure(HttpSecurity http) throws Exception {
	  http
	   .headers().disable()
	   .csrf().disable()
	   .authorizeRequests()
	   	.antMatchers("/rest/api/v1/Users/Register").permitAll()
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
	    .deleteCookies("JSESSIONID")
	    .permitAll()
	    .and();
	  http.authorizeRequests().antMatchers("/login*").permitAll();
	 }
}
