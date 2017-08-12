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

import com.bam.beans.Batch;
import com.bam.beans.Users;
import com.bam.dao.BatchRepository;
import com.bam.dao.UsersRepository;

@Service("userDetailsService")
@Transactional
public class UsersDetailsService implements UserDetailsService {
	
	
	@Autowired
	UsersRepository dao;

	@Autowired
	BatchRepository bdao;

	public void addOrUpdateUser(Users user) {
		dao.save(user);

	}

	public List<Users> findAllUsers() {
		return dao.findAll();

	}

	public List<Users> findByRole(int role) {
		return dao.findByRole(role);
	}

	public Users findUserById(int userId) {
		return dao.findByUserId(userId);
	}

	public Users findUserByEmail(String email) {
		return dao.findByEmail(email);
	}

	/**
	 * Get batch object by the id
	 * Return users in the batch
	 * @param batchId
	 * @return
	 */
	public List<Users> findUsersInBatch(int batchId) {
		
		Batch batch = bdao.findById(batchId);
		
		return dao.findByBatch(batch);
	}

	/**
	 * Returns users in the batch with a null
	 */
	public List<Users> findUsersNotInBatch() {
		List<Users> users = dao.findByBatch(null);
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getRole() != 1) {
				users.remove(i);
				i--;
			}
		}
		return users;
	}
	
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		Users user = dao.findByEmail(email);
		System.out.println(email);

		return buildUserForAuthentication(user, buildUserAuthority(user));
	}

	/**
	 * 
	 * @param user
	 * @param authorities
	 * @return
	 * Converts Users user to org.springframework.security.core.userdetails.User
	 */
	private User buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPwd(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Users u) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		setAuths.add(new SimpleGrantedAuthority("ROLE_" + String.valueOf(u.getRole())));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}