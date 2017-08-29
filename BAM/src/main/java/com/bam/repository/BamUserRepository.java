package com.bam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.bean.Batch;
import com.bam.bean.BamUser;

@Repository
public interface BamUserRepository extends JpaRepository<BamUser, Integer>{
	public BamUser findByUserId(int id);
	public BamUser findByEmail(String email);
	public List<BamUser> findByBatch(Batch batch);
	public List<BamUser> findByRole(int role);
	
	public List<BamUser> findByFNameAndLName(String f, String l);
}


