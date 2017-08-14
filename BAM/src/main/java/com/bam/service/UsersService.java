
package com.bam.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bam.bean.Batch;
import com.bam.bean.BamUser;
import com.bam.repository.BatchRepository;
import com.bam.repository.BamUserRepository;

@Transactional
public class UsersService {

	@Autowired
	BamUserRepository bamUserRepository;

	@Autowired
	BatchRepository batchRepository;

	public void addOrUpdateUser(BamUser user) {
	  bamUserRepository.save(user);

	}

	public List<BamUser> findAllUsers() {
		return bamUserRepository.findAll();

	}

	public List<BamUser> findByRole(int role) {
		return bamUserRepository.findByRole(role);
	}

	public BamUser findUserById(int userId) {
		return bamUserRepository.findByUserId(userId);
	}

	public BamUser findUserByEmail(String email) {
		return bamUserRepository.findByEmail(email);
	}

	public List<BamUser> findUsersInBatch(int batchId) {
		// Get batch object by the id
		Batch batch = batchRepository.findById(batchId);
		// Return users in the batch
		return bamUserRepository.findByBatch(batch);
	}

	public List<BamUser> findUsersNotInBatch() {
		// Return users in the batch with a null
		List<BamUser> users = bamUserRepository.findByBatch(null);
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getRole() != 1) {
				users.remove(i);
				i--;
			}
		}
		return users;
	}

}
