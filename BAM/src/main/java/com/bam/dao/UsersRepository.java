package com.bam.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.Users;
import java.lang.String;
import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{
	public Users findByUserId(Integer id);
	public Users findByEmail(String email);
}


