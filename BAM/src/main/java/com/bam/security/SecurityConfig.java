package com.bam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/static/**").permitAll();
        http
          .authorizeRequests()
          	.antMatchers("/login*").anonymous()
          	.anyRequest().authenticated()
          	.and()
          .formLogin()
          	.permitAll()
          	.failureUrl("/login.html?error=true")
          	.and()
          	.csrf().disable();
        
    }
}