package com.bam.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bam.beans.Users;

@Service("userDetailsService")
public class UsersDetailsService implements UserDetailsService {

	// Get user from the database, via Hibernate
	@Autowired
	private UsersService dao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		Users user = dao.findUserByEmail(email);
		System.out.println(email);
		System.out.println(user);
		
		return buildUserForAuthentication(user, buildUserAuthority(user));
	}

	// Converts Users user to org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPwd(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Users u) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		setAuths.add(new SimpleGrantedAuthority(String.valueOf(u.getRole())));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}