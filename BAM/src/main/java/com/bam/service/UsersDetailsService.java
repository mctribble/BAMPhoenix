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

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.repository.BamUserRepository;
import com.bam.repository.BatchRepository;

@Service("userDetailsService")
@Transactional
public class UsersDetailsService implements UserDetailsService {

	@Autowired
	BamUserRepository dao;

	@Autowired
	BatchRepository bdao;

	public void addOrUpdateUser(BamUser user) {
		dao.save(user);

	}

	public List<BamUser> findAllUsers() {
		return dao.findAll();

	}

	public List<BamUser> findByRole(int role) {
		return dao.findByRole(role);
	}

	public BamUser findUserById(int userId) {
		return dao.findByUserId(userId);
	}

	public BamUser findUserByEmail(String email) {
		return dao.findByEmail(email);
	}

	/**
	 * Get batch object by the id Return users in the batch
	 * 
	 * @param batchId
	 * @return
	 */
	public List<BamUser> findUsersInBatch(int batchId) {

		Batch batch = bdao.findById(batchId);

		return dao.findByBatch(batch);
	}

	/**
	 * Returns users in the batch with a null
	 */
	public List<BamUser> findUsersNotInBatch() {
		List<BamUser> users = dao.findByBatch(null);
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getRole() != 1) {
				users.remove(i);
				i--;
			}
		}
		return users;
	}

	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		BamUser user = dao.findByEmail(email);

		return buildUserForAuthentication(user, buildUserAuthority(user));
	}

	/**
	 * 
	 * @param user
	 * @param authorities
	 * @return Converts Users user to
	 *         org.springframework.security.core.userdetails.User
	 */
	private User buildUserForAuthentication(BamUser user, List<GrantedAuthority> authorities) {
		return new User(user.getEmail(), user.getPwd(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(BamUser u) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		setAuths.add(new SimpleGrantedAuthority("ROLE_" + String.valueOf(u.getRole())));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
<<<<<<< HEAD
	public void recoverE(BamUser user){
		
		 Runnable run = new EmailRun("revatbam@gmail.com");
		 Thread th = new Thread(run);
		 th.start();
=======

  /*
      Author: Adeo Salam
  */
	public void recoverE(BamUser user) {
		EmailRun er = new EmailRun();
		//hard coded for testing purposes
		//user.setEmail("revatbam@gmail.com"); use this user for functionality
		er.setUser(user);
		Thread th = new Thread(er);
		th.start();
>>>>>>> 2220584cbc57f69ec51688b7e290250e3c8a8f50
	}

}