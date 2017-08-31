package com.bam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bam.service.BamUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	BamUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler restAuthenticationFailureHandler;
	
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	/***
	 * @author Nam Mai
	 * Configure the passwordEncoder to use the BCrypt hashing algorithm
	 */
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/***
	 * @author Nam Mai
	 * This method encodes the password upon authentication
	 */
	@Bean
	public DaoAuthenticationProvider authProvider(){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	 @Override
	 public void configure(WebSecurity web) throws Exception {
		// Ignore certain URLs.
		web.ignoring().antMatchers("/index.html", "/static/**", "/");
	 }


	/***
	 * @author Nam Mai
	 * References the authProvider to enable hashing
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth){
		auth.authenticationProvider(authProvider());
	}
	
	/**
	 * @author Duncan Hayward
	 * uncomment to protect rest endpoints, need to fix the roles first 
	 * logout isn't getting deleting of JSESSIONID
	 * Don't disable csrf in production
	 */
	@Override
	 protected void configure(HttpSecurity http) throws Exception {
	  http
	   .headers().disable()
	   .csrf().disable()
//	   .exceptionHandling()
//	   .authenticationEntryPoint(authenticationEntryPoint)
//	   .and()
	   .authorizeRequests()
	    .antMatchers("/rest/api/v1/Users/Register").permitAll()
//	    .antMatchers("**rest*/**").authenticated()
//	    .antMatchers("*rest*/**").authenticated()
//	    .antMatchers("**/*rest*/**").authenticated()
//	    .antMatchers("**rest*/**").authenticated()
	    .anyRequest().authenticated()
//	    .antMatchers("/rest/api/v1/Curriculum/**").hasAuthority("Trainer")
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
	    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	    .logoutSuccessUrl("/logout").deleteCookies("JSESSIONID")
	    .invalidateHttpSession(true);		
	 }
}
