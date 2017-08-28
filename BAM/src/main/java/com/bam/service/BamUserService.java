package com.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.repository.BamUserRepository;
import com.bam.repository.BatchRepository;

@Service
public class BamUserService {

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

  /*
   * Author: Adeo Salam
   */
  public void recoverE(BamUser user, String unhashedPwd) {
    EmailRun er = new EmailRun();
    user.setPwd(unhashedPwd);
    er.setUser(user);
    Thread th = new Thread(er);
    th.start();
  }

  /**
   * Service method for calling spring data repository method. Finds user with
   * given firstname and lastname.
   * 
   * 
   * @author DillonT, GilB
   * @param f
   * @param l
   * @return
   */
  public List<BamUser> getByFNameAndLName(String f, String l) {
    return bamUserRepository.findByFNameAndLName(f, l);
  }

}
