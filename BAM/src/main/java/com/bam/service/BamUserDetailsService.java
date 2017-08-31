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

import com.bam.bean.BamUser;
import com.bam.repository.BamUserRepository;
 
@Service
public class BamUserDetailsService implements UserDetailsService {

  @Autowired
  private BamUserService bamUserService;

  @Autowired
  private BamUserRepository bamUserRepository;

  /**
   * Returns users in the batch with a null
   */
  public List<BamUser> findUsersNotInBatch() {
    List<BamUser> users = bamUserRepository.findByBatch(null);
    for (int i = 0; i < users.size(); i++) {
      if (users.get(i).getRole() != 1) {
        users.remove(i);
        i--;
      }
    }
    return users;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    BamUser user = bamUserService.findUserByEmail(email);
    return buildUserForAuthentication(user, buildUserAuthority(user));
  }

  // Converts Users user to org.springframework.security.core.userdetails.User
  private User buildUserForAuthentication(BamUser user, List<GrantedAuthority> authorities) {
    return new User(user.getEmail(), user.getPwd(), true, true, true, true, authorities);
  }

  private List<GrantedAuthority> buildUserAuthority(BamUser user) {
    Set<GrantedAuthority> setAuths = new HashSet<>();
    // Build user's authorities
    setAuths.add(new SimpleGrantedAuthority("ROLE_" + String.valueOf(user.getRole())));
    //List<GrantedAuthority> result = new ArrayList<>(setAuths);
    //return result;
    return new ArrayList<>(setAuths);
  }

}